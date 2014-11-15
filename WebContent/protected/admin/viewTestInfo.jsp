<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="siteTags" tagdir="/WEB-INF/tags"%>

<c:import url="../layouts/header.jsp"></c:import>
<style>
#correct {
	font-weight: bold;
	color: green;
}

#incorrect {
	font-weight: bold;
	color: red;
}
</style>
<siteTags:header pageTitle="Test Details" />

<body>
	<div class="uk-width-medium-1-2 uk-container-center">
		<nav class="uk-navbar">

			<ul class="uk-navbar-nav ">


				<li><a href="protected/admin/editQuizes.jsp">Home</a></li>
				<li><a href="protected/admin/createUser.jsp">Create Candidate</a></li>
				<li><a href="protected/admin/createQuiz.jsp">Create Quiz</a></li>
				<li><a href="CandidateServlet?displayCandidates">View
						Candidates</a></li>
				<li><a href="LoginServlet">Sign out</a></li>
			</ul>
		</nav>


		<div class="uk-width-1-1">
			<br />
			<h1>Test Details</h1>
		</div>
		<div class="uk-width-1-1">
			<h4>Start time: ${test.getStartTime() }</h4>
			<h4>End time: ${test.getEndTime() }</h4>
			<h4>Score: ${test.getScore() } %</h4>
			<h4>Quiz : ${quizName}</h4>
		</div>
		<div class="uk-width-1-1">
			<ul class="uk-list uk-list-striped uk-width-medium-1-1">
				<c:forEach var="question" items="${questions}">
					<li><c:out value="${question.getText()}" escapeXml="true" /><br />
						<br /> <c:forEach var="answer" items="${question.getAnswers()}">
							<c:choose>
								<c:when
									test="${questionsAnswersMap.get(question.getId())==answer.getId() and answer.isCorrect()}">
									<div id="correct">
										<c:out value="${ answer.getText()}" escapeXml="true" />
									</div>
								</c:when>
								<c:when
									test="${questionsAnswersMap.get(question.getId())==answer.getId() and not answer.isCorrect()}">
									<div id="incorrect">
										<c:out value="${ answer.getText()}" escapeXml="true" />
									</div>
								</c:when>
								<c:when test="${answer.isCorrect() }">
									<div id="correct">
										<c:out value="${ answer.getText()}" escapeXml="true" /> <b style="color:red">not selected</b>
									</div>
								</c:when>
								<c:otherwise>

									<c:out value="${ answer.getText()}" escapeXml="true" />
								</c:otherwise>
							</c:choose>

							<br />
						</c:forEach></li>
					<hr>
				</c:forEach>

			</ul>
		</div>



		<jsp:include page="../layouts/footer.jsp"></jsp:include>
	</div>