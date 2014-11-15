<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="siteTags" tagdir="/WEB-INF/tags"%>
<jsp:include page="../layouts/header.jsp"></jsp:include>


<siteTags:header pageTitle="Edit Quizes" />


<body>
	<div class="uk-width-medium-1-2 uk-container-center">
		<nav class="uk-navbar">
			<ul class="uk-navbar-nav ">
				<li class="uk-active"><a href="#">Home</a></li>
				<li><a href="createUser.jsp">Create Candidate</a></li>
				<li><a href="createQuiz.jsp">Create Quiz</a></li>
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
		<c:if test="${notifyM!=null }">
			<div class="uk-alert uk-alert-success" data-uk-alert>
				<a href="" class="uk-alert-close uk-close"></a>
				<p>${notifyM}</p>
			</div>
			<c:remove var="notifyM" scope="session"></c:remove>
		</c:if>
		<div class="uk-width-1-1">
			<br />
			<h2>Select a quiz from the following list for edit:</h2>
			<br />
			<ol class="uk-list uk-list-striped uk-width-medium-1-1">
				<c:forEach var="quiz" items="${quizes}">

					<li><h3>
							<a style="text-decoration: none"
								href='../../EditQuizServlet?id=${quiz.getId()}'>${quiz.getName()}</a>
						</h3></li>
				</c:forEach>

			</ol>
		</div>


		<jsp:include page="../layouts/footer.jsp"></jsp:include>
	</div>