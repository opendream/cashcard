<%@ page import="th.co.opendream.cashcard.domain.InterestRate" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'interestRate.label', default: 'InterestRate')}" />
		<title>Edit</title>
	</head>
	<body>
		<div class="container">
			<header class="page-header">
				<h1>Edit</h1>
			</header>
		</div>
		<g:render template="toolbar"/>

		<div id="edit-interestRate" class="content scaffold-edit" role="main">
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${interestRateInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${interestRateInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<div class="container">
				<g:form method="post" class="form-horizontal">
					<g:hiddenField name="id" value="${interestRateInstance?.id}" />
					<g:hiddenField name="version" value="${interestRateInstance?.version}" />
					<g:render template="form"/>
					<div class="form-actions">
						<button class="btn btn-primary" type="submit"><i class="icon-ok icon-white"></i> ${message(code: 'default.button.update.label', default: 'Update')}</button>
						<button class="btn" type="submit" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" ><i class="icon-trash"></i> ${message(code: 'default.button.delete.label', default: 'Delete')}</button>
					</div>
				</g:form>
			</div>
		</div>
	</body>
</html>
