<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="siteTags" tagdir="/WEB-INF/tags"%>

<c:import url="../layouts/header.jsp"></c:import>
<siteTags:header pageTitle="Edit Question" />

<body>
	<div class="uk-width-medium-1-2 uk-container-center">
		<nav class="uk-navbar">
			<ul class="uk-navbar-nav ">
				<li ><a href="protected/admin/editQuizes.jsp">Home</a></li>
				<li><a href="protected/admin/createUser.jsp">Create Candidate</a></li>
				<li><a href="protected/admin/createQuiz.jsp">Create Quiz</a></li>
				<li><a href="CandidateServlet?displayCandidates">View
						Candidates</a></li>
				<li><a href="LoginServlet">Sign out</a></li>
			</ul>
		</nav>
		<div class="uk-width-1-1">
			<br />
			<h2>Edit Question</h2>
		</div>

		<form method="post" action="EditQuestionServlet">

			<div class="uk-form-row">
				Question Title: <input name="questionTitle"
					value="${fn:escapeXml(question.getTitle())}" />
			</div>
			<div class="uk-form-row">
				Question Text:
				<textarea rows="3" cols="50" name="questionText">${fn:escapeXml(question.getText())}</textarea>
			</div>

			<div class="uk-form-row">
				<c:if test="${question.getOrdered()==false}">
					<label>Ordered Answers <input type="checkbox" value=true
						name="answersOrdered" /></label>
				</c:if>
				<c:if test="${question.getOrdered()==true }">
					Ordered Answers <input type="checkbox" value=true
						name="answersOrdered" checked />
				</c:if>
			</div>
			<p>Answers</p>
			<c:forEach var="answer" items="${question.getAnswers()}">
				<div class="uk-form-row">
					<textarea rows="3" cols="50" name="aId${answer.getId()}">${fn:escapeXml(answer.getText())}</textarea>


					<c:if test="${answer.isCorrect()==true}">
						<label><input type="checkbox" value=true checked
							name="correct${answer.getId()}">Correct</label>
					</c:if>
					<c:if test="${answer.isCorrect()==false}">
						<label><input type="checkbox" value=true
							name="correct${answer.getId()}">Correct</label>
					</c:if>
				</div>
			</c:forEach>
			<input type="hidden" value="${question.getId()}" name="questionId">
			<input type="hidden" value="${question.getQuizId()}" name="quizId">
			<input type="submit" name="submit"
				class="uk-button uk-button-primary" value="Update">

		</form>


		<jsp:include page="../layouts/footer.jsp"></jsp:include>
	</div>