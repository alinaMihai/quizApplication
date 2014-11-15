<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="siteTags" tagdir="/WEB-INF/tags"%>

<c:import url="../layouts/header.jsp"></c:import>
<siteTags:header pageTitle="Candidates List" />

<body>
	<div class="uk-width-medium-1-2 uk-container-center">
		<nav class="uk-navbar">

			<ul class="uk-navbar-nav ">
				<li><a href="editQuizes.jsp">Home</a></li>
				<li><a href="createUser.jsp">Create
						Candidate</a></li>
				<li><a href="createQuiz.jsp">Create Quiz</a></li>
				<li class="uk-active"><a href="#">View Candidates</a></li>
				<li><a href="../../LoginServlet">Sign out</a></li>
			</ul>
		</nav>
		<c:remove var="quizName"></c:remove>
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

		<div class="uk-width-1-1">
			<br />
			<h1>List of Candidates</h1>
		</div>
		<div class="uk-width-1-1">
			<ol class=" uk-list-striped uk-width-medium-1-1">
				<c:forEach var="candidate" items="${candidates}">

					<li><h3>
							<a style="text-decoration: none"
								href='../../CandidateServlet?getTests&id=${candidate.getId()}'>${candidate.getName()}</a>
						<a class="uk-button uk-float-right"  href="../../AddQuizToCandidateServlet?id=${candidate.getId()}">+Add New Quizes</a>
						</h3>
						
					</li>
				</c:forEach>

			</ol>
		</div>



		<jsp:include page="../layouts/footer.jsp"></jsp:include>
	</div>