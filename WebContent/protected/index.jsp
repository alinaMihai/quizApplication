<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="siteTags" tagdir="/WEB-INF/tags"%>

<c:import url="layouts/header.jsp"></c:import>

<siteTags:header pageTitle="Quizes Listing" />
<body>
	<div class="uk-width-medium-1-2 uk-container-center">
		<nav class="uk-navbar">

			<ul class="uk-navbar-nav ">
				<li><a href="LoginServlet">Sign out</a></li>
			</ul>
		</nav>
		<div class="uk-width-1-1">
			<br />
			<h1>Welcome</h1>
		</div>
		<br />
		<div class="uk-grid">

			<div class="uk-width-1-1">
				<h2>Select a quiz from the following list to start the test:</h2>
				<br />
			</div>
			<div class="uk-width-1-1">

				<ol class="uk-list uk-list-striped uk-width-medium-1-1">
					<c:forEach var="quiz" items="${quizes}">

						<li><h3>
								<a style="text-decoration: none"
									href='QuizServlet?id=${quiz.getId()}'>${quiz.getName()}</a>
							</h3></li>
					</c:forEach>

				</ol>
			</div>


		</div>

		<c:import url="layouts/footer.jsp"></c:import>
	</div>