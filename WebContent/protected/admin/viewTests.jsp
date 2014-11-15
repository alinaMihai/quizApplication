<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="siteTags" tagdir="/WEB-INF/tags"%>

<c:import url="../layouts/header.jsp"></c:import>
<siteTags:header pageTitle="Tests List" />

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

		<div class="uk-width-1-1">
			<br />
			<h1>List of Tests of candidate ${candidateName}</h1>
		</div>
		<div class="uk-width-1-1">
			<ul class="uk-list uk-list-striped uk-width-medium-1-1">
				<c:forEach var="test" items="${tests}">
					<c:if test="${test.getStartTime()!=null }">
						<li><h3>

								<a style="text-decoration: none"
									href='CandidateServlet?getTestInfo&id=${test.getId()}'><b>${test.getQuizName()}</b>
									on ${test.getStartTime()}</a>

							</h3></li>
					</c:if>
					<c:if test="${test.getStartTime() == null}">
					<h3>${test.getQuizName()} not yet taken</h3>
					</c:if>
				</c:forEach>

			</ul>
		</div>



		<jsp:include page="../layouts/footer.jsp"></jsp:include>
	</div>