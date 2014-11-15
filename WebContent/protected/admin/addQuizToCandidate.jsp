<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="siteTags" tagdir="/WEB-INF/tags"%>

<jsp:include page="../layouts/header.jsp"></jsp:include>


<siteTags:header pageTitle="Add New Quiz to Candidate" />

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
		<h1>Assign new quizes to candidate</h1>
		<form class="uk-form" method='post'
			action='AddQuizToCandidateServlet'>
			<div class="uk-form-row">
				<c:forEach var="quiz" items="${newQuizes}">
					<label><input type='checkbox' value="${quiz.getId()}"
						name="quiz" /> ${quiz.getName()}</label>
					<br />
				</c:forEach>
			</div>
			<br/>
            <input type="hidden" name="candidateId" value="${candidateId}">
			<div class="uk-form-row">
				<input class="uk-button-primary" type="submit" name="submit"
					value="Commit">
			</div>
		</form>
		<br />
		<jsp:include page="../layouts/footer.jsp"></jsp:include>
	</div>