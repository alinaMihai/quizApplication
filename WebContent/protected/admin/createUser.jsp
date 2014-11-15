<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="siteTags" tagdir="/WEB-INF/tags"%>
<jsp:include page="../layouts/header.jsp"></jsp:include>



<siteTags:header pageTitle="Create Candidate" />

<body>

	<div class="uk-width-medium-1-2 uk-container-center">
		<nav class="uk-navbar">

			<ul class="uk-navbar-nav ">


				<li><a href="editQuizes.jsp">Home</a></li>
				<li class="uk-active"><a href="createUser.jsp">Create
						Candidate</a></li>
				<li><a href="createQuiz.jsp">Create Quiz</a></li>
				<li><a href="../../CandidateServlet?displayCandidates">View
						Candidates</a></li>
				<li><a href="../../LoginServlet">Sign out</a></li>
			</ul>
		</nav>
		<c:if test="${noticeM!=null }">
			<div class="uk-alert uk-alert-success" data-uk-alert>
				<a href="" class="uk-alert-close uk-close"></a>
				<p>${noticeM}</p>
			</div>
			<c:remove var="noticeM" scope="session"></c:remove>
		</c:if>

		</br>
		<h1>Create Candidate</h1>
		<form class="uk-form" method='post' action='../../UserServlet'>
			<div class="uk-form-row">
				<input type="text" placeholder="name" name="cname" required>
			</div>
			<div class="uk-form-row">
				<input type="text" placeholder="username" name="username" required>
			</div>
			<div class="uk-form-row">
				<input type="password" placeholder="password" name="password"
					required>
			</div>

			<div class="uk-form-row">
				<p>Assign quizes to candidate</p>
				<c:forEach var="quiz" items="${quizes}">

					<label><input type='checkbox' value="${quiz.getId()}"
						name="quiz" /> ${quiz.getName()}</label>
					<br />

				</c:forEach>

			</div>


			<div class="uk-form-row">
				<input class="uk-button-primary" type="submit" name="submit"
					value="Create">

			</div>
		</form>

		<br />
		<jsp:include page="../layouts/footer.jsp"></jsp:include>
	</div>