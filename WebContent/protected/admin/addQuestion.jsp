<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="siteTags" tagdir="/WEB-INF/tags"%>

<jsp:include page="../layouts/header.jsp"></jsp:include>


<siteTags:header pageTitle="Add Question" />

<body>
	<div class="uk-width-medium-1-2 uk-container-center">
		<nav class="uk-navbar">

			<ul class="uk-navbar-nav ">


				<li><a href="DisplayQuizesServlet">Home</a></li>
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
		</br>
		<h1>Add Question to Quiz ${quizName}</h1>
		<form class="uk-form" method='post' action='QuestionServlet'>
			<div class="uk-form-row">
				<input type="text" placeholder="title" name="title" required>
			</div>
			<div class="uk-form-row">
				<textarea rows="5" cols="100" name="text"
					placeholder="question text" required></textarea>
			</div>
			<div class="uk-form-row">
				<label>Order possible answers <br /> <input type="checkbox"
					name="ordered" value="true"></label>
			</div>

			<div class="uk-form-row">
				<input class="uk-button-primary" type="submit" name="submit"
					value="Add">
			</div>
			<input type="hidden" name="quizId" required value=${quizId}>
		</form>

		<br />
		<jsp:include page="../layouts/footer.jsp"></jsp:include>
	</div>