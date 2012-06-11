<%@ page import="th.co.opendream.cashcard.InterestRate" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'interestRate.label', default: 'InterestRate')}" />
		<title><g:message code="interestRate.create.title"></g:message></title>
	</head>
	<body>
		<div class="container">
			<header class="page-header">
				<h1><g:message code="interestRate.create.title"></g:message></h1>
			</header>
		</div>
		<g:render template="toolbar"/>

		<div id="create-interestRate" class="content scaffold-create" role="main">
			<g:if test="${flash.message}">
				<div class="message alert alert-success" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${interestRateInstance}">
				<div id="errors" class="alert alert-error">
		          <g:renderErrors bean="${interestRateInstance}" as="list"></g:renderErrors>
			    </div><!-- /errors -->
			</g:hasErrors>
			<div class="container" >
				<g:form action="save" class="form-horizontal" useToken="true">
					<g:render template="form"/>
						<div class="form-actions">
							<button class="btn btn-primary" type="submit"><i class="icon-ok icon-white"></i> ${message(code: 'default.button.create.label', default: 'Create')}</button>
							<g:link action="list"><g:message code="default.button.cancel.label"></g:message></g:link>
						</div>
				</g:form>
			</div>
		</div>
	</body>
</html>
