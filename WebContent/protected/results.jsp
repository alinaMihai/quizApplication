<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix="siteTags" tagdir="/WEB-INF/tags"%>

<c:import url="layouts/header.jsp"></c:import>
<siteTags:header pageTitle="Results" />



<body class="uk-height-1-1">
	<div class="uk-vertical-align uk-text-center uk-height-1-1">
		<div class="uk-vertical-align-middle" style="width: 250px;">


			<h4>Here are your Test results</h4>

			<h4>
				You got <b>${score} %</b>
			</h4>
			<h4>${timeMessage}</h4>

			<br />

			<p>
				Go back to the quizes list <a href="DisplayQuizesServlet"> Back</a>
			</p>
			<br />
			<jsp:include page="layouts/footer.jsp"></jsp:include>
		</div>
	</div>