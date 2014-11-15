
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="siteTags" tagdir="/WEB-INF/tags"%>

<c:import url="protected/layouts/header.jsp"></c:import>
<siteTags:header pageTitle="Login" />


<body class="uk-height-1-1">
	<div class="uk-vertical-align uk-text-center uk-height-1-1">
		<div class="uk-vertical-align-middle" style="width: 250px;">

			<c:if test="${errorMessage!=null }">
				<div class="uk-alert uk-alert-danger" data-uk-alert>
					<a href="" class="uk-alert-close uk-close"></a>
					<p>${errorMessage}</p>
				</div>
				<c:remove var="errorMessage" />
			</c:if>


			<form method='post' action='LoginServlet'
				class="uk-panel uk-panel-box uk-form">
				<div class="uk-form-row">
					<input name='username' class="uk-width-1-1 uk-form-large"
						type="text" placeholder="Username" value="${username }">
				</div>
				<div class="uk-form-row">
					<input name='password' class="uk-width-1-1 uk-form-large"
						type="password" placeholder="Password">
				</div>
				<div class="uk-form-row">
					<input type='submit'
						class="uk-width-1-1 uk-button uk-button-primary uk-button-large"
						value="Login"></input>
				</div>

			</form>

		</div>
		<c:import url="protected/layouts/footer.jsp"></c:import>
	</div>