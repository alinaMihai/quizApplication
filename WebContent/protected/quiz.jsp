<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="siteTags" tagdir="/WEB-INF/tags"%>

<c:import url="layouts/header.jsp"></c:import>

<siteTags:header pageTitle="Test:${quizObject.getName()}" />


<body>
	<div class="uk-width-medium-1-2 uk-container-center">
		<nav class="uk-navbar">

			<ul class="uk-navbar-nav ">
				<li><a href="LoginServlet">Sign out</a></li>
			</ul>
		</nav>
		<br />
		<div class="uk-width-1-1">
			<p>Progress Bar</p>
			<p id="activateJavascript" class="uk-alert uk-alert-danger">
				JavaScript needs to be enabled to take the quiz <br />Activate
				JavasScript and refresh the page
			</p>
			<c:if test="${quizObject.getTimeLimit()!=0}">
				<div class="uk-progress uk-progress-striped uk-active">
					<div id="progressBar" class="uk-progress-bar" style="width: 0%;">
						<span id="timeLimit">${quizObject.getTimeLimit()}</span>
					</div>
				</div>
			</c:if>

		</div>
		<div class="uk-width-1-1">
			<br />
			<h1>${quizObject.getName()}</h1>

		</div>
		<form method='post' action='ProcessQuizServlet' id="quizForm">
			<ol>
				<c:forEach var="question" items="${questions}">
					<li><c:out value="${question.getText()}" escapeXml="true" /><br />
						<br /> <c:forEach var="answer" items="${question.getAnswers()}">

							<c:choose>
								<c:when test="${quizObject.getType() == 1}">

									<label> <input type='checkbox'
										name="CId${question.getId()}" value="${answer.getId()}">

										<c:out value="${ answer.getText()}" escapeXml="true" />
									</label>

									<br />
								</c:when>
								<c:otherwise>

									<label><input type='radio'
										name="RId${question.getId()}" value="${answer.getId()}">
										<c:out value="${ answer.getText()}" escapeXml="true" /> </label>
									<br />
								</c:otherwise>
							</c:choose>
						</c:forEach></li>
					<hr>
				</c:forEach>
			</ol>
			<input type="hidden" name="quizId" value="${quizObject.getId() }">
			<input class="uk-button uk-button-primary" type='submit'
				value='Submit Quiz'></input>
		</form>
		<br /> <br /> Copyright @2014 Incrys Internship
	</div>

	<script
		src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/uikit/2.10.0/js/uikit.js"></script>
	<script
		src="//cdnjs.cloudflare.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>

	<script>
		(function() {

			$("#activateJavascript").remove();

			var progressBar = $("#progressBar");
			var timeLimitElement = $("#timeLimit");
			var duration = parseInt(timeLimitElement.text());
			var durationToSeconds = duration * 60;

			var start = new Date();
			var maxTime = durationToSeconds * 1000;
			var timeoutVal = Math.floor(maxTime / 100);
			var countSecounds = 0;
			var counter = null;

			function updateProgress(percentage) {

				progressBar.css("width", percentage + "%");
				timeLimitElement.text(percentage + "%");
			}

			function animateUpdate() {
				var now = new Date();
				var timeDiff = now.getTime() - start.getTime();
				var perc = Math.round((timeDiff / maxTime) * 100);

				if (perc <= 100) {
					updateProgress(perc);
					setTimeout(animateUpdate, timeoutVal);
				}
			}

			function createDialog(message) {
				var newDiv = $("<div title='Warning'></div>");
				newDiv.html(message);
				newDiv.dialog({
					dialogClass : "no-close",
					buttons : [ {
						text : "OK",
						click : function() {
							$(this).dialog("close");
						}
					} ]
				});
			}

			window.onload = function() {

				if (!isNaN(duration)) {
					animateUpdate();

					counter = setInterval(
							function() {
								countSecounds++;
								console.log(countSecounds, durationToSeconds);
								// notify the user when there are 5 minutes left
								if (countSecounds == durationToSeconds - 300) {
									createDialog("You have 5 more minutes. Please prepare to submit the quiz");

								} else if (countSecounds == durationToSeconds) {
									createDialog("Time is up. The quiz will be automatically submited");

									clearInterval(counter);
									document.forms["quizForm"].submit();
								}

							}, 1000);

					$("quizForm").submit(function(ev) {
						clearInterval(counter);
					});
				}
			};

		})();
	</script>
</body>
</html>