<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="siteTags" tagdir="/WEB-INF/tags"%>

<jsp:include page="../layouts/header.jsp"></jsp:include>

<style>

</style>

<siteTags:header pageTitle="Create Quiz" />

<body>
	<div class="uk-width-medium-1-2 uk-container-center">
		<nav class="uk-navbar">

			<ul class="uk-navbar-nav ">
				<li><a href="editQuizes.jsp">Home</a></li>
				<li><a href="createUser.jsp">Create Candidate</a></li>
				<li class="uk-active"><a href="createQuiz.jsp">Create Quiz</a></li>
				<li><a href="../../CandidateServlet?displayCandidates">View
						Candidates</a></li>
				<li><a href="../../LoginServlet">Sign out</a></li>
			</ul>
		</nav>
	
		<c:if test="${errorM!=null }">
			<div class="uk-alert uk-alert-danger" data-uk-alert>
				<a href="" class="uk-alert-close uk-close"></a>
				<p>${errorM}</p>
			</div>
			<c:remove var="errorMessage" />
		</c:if>
		</br>
		<h1>Create Quiz</h1>
		<form class="uk-form" method='post' action='../../CreateQuizServlet'>
			<div class="uk-form-row">
				<label><input type="text" placeholder="name" name="qname"
					required value=${qname}></label>
			</div>
			<div class="uk-form-row">
				<label>Ordered List <input type="checkbox" name="ordered"
					value="true"></label>
			</div>

			<div class="uk-form-row">
				<input type="number" placeholder="time limit in minutes"
					name="timeLimit" required>
			</div>
			<div class="uk-form-row">
				Type<br /> <label><input type="radio" name="type" value=0>
					Simple<br /></label> <label><input type="radio" name="type"
					value=1> Multiple Choice</label>
			</div>
			<div class="uk-form-row">
				Comments
				<textarea rows="5" cols="100" name="comments">${comments }</textarea>
			</div>
			<div class="uk-form-row">
				<input class="uk-button-primary" type="submit" name="submit"
					value="Create">
			</div>
		</form>

		<br />
		<jsp:include page="../layouts/footer.jsp"></jsp:include>
	</div>