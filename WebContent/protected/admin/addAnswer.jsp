<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="siteTags" tagdir="/WEB-INF/tags"%>

<jsp:include page="../layouts/header.jsp"></jsp:include>


<siteTags:header pageTitle="Add Answer" />

<body>
	<div class="uk-width-medium-1-2 uk-container-center">
		<nav class="uk-navbar">

			<ul class="uk-navbar-nav ">


				<li><a href="protected/admin/editQuizes.jsp">Home</a></li>
				<li><a href="protected/admin/createUser.jsp">Create
						Candidate</a></li>
				<li><a href="protected/admin/createQuiz.jsp">Create Quiz</a></li>
				<li><a href="CandidateServlet?displayCandidates">View
						Candidates</a></li>
				<li><a href="LoginServlet">Sign out</a></li>
			</ul>
		</nav>
		<c:if test="${errorM!=null }">
			<div class="uk-alert uk-alert-danger" data-uk-alert>
				<a href="" class="uk-alert-close uk-close"></a>
				<p>${errorM}</p>
			</div>
			<c:remove var="errorMessage" />
		</c:if>
		<c:if test="${notifyM!=null }">
			<div class="uk-alert uk-alert-success" data-uk-alert>
				<a href="" class="uk-alert-close uk-close"></a>
				<p>${notifyM}</p>
			</div>
			<c:remove var="notifyM" scope="session"></c:remove>
		</c:if>
		<br />

		<h1>Add Possible Answers</h1>
		<form class="uk-form" method='post' action='AnswerServlet'>

			<c:forEach var="i" begin="1" end="5">
				<div class="uk-form-row">
					<textarea rows="5" cols="100" name="a${i}" placeholder="text answer ${i}"></textarea>
					
					
					<c:if test="${quizType eq 1}">
						<label>Is Correct? <input type="checkbox" name="check" value="${i}"></label>
					</c:if>
					
					<c:if test="${quizType eq 0}">
						<label>Is Correct? <input type="radio" name="radio" value="${i}"></label>
					</c:if>
				</div>
			</c:forEach>


			<div class="uk-form-row">
				<input class="uk-button-primary" type="submit" name="submit"
					value="Add">
			</div>
			<input type="hidden" name="questionId" required value=${questionId}>
		</form>

		<br />
		<jsp:include page="../layouts/footer.jsp"></jsp:include>
	</div>