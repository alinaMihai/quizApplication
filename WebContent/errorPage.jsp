<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="siteTags" tagdir="/WEB-INF/tags"%>

<c:import url="protected/layouts/header.jsp"></c:import>
<siteTags:header pageTitle="Error Page" />

<body class="uk-height-1-1">
	<div class="uk-vertical-align uk-text-center uk-height-1-1">
		<div class="uk-vertical-align-middle" style="width: 250px;">
		
		
		<div class="uk-alert uk-alert-danger">
		<b>Error</b>
		<p><h4>Something went wrong.<br/> The operation was not successful</h4></p>
		</div>
		
		
		</div>
		<c:import url="protected/layouts/footer.jsp"></c:import>
	</div>