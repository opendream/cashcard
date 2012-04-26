
<%@ page import="th.co.opendream.cashcard.MemberHistory" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'memberHistory.label', default: 'MemberHistory')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="list-memberHistory" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div class="container">
					<table class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th><g:message code="member.label.identificationNumber"></g:message></th>
								<th><g:message code="member.label.name"></g:message></th>
								<th><g:message code="member.label.balance"></g:message></th>
								<th><g:message code="member.label.interest"></g:message></th>
								<th><g:message code="member.label.telNo"></g:message></th>
								<th><g:message code="member.label.gender"></g:message></th>
								<th><g:message code="member.label.address"></g:message></th>
								<th><g:message code="member.label.status"></g:message></th>
							</tr>
						</thead>
						<tbody>
							<g:each var="memberHistory" in ="${MemberHistory.list()}">
								<tr>
									<td><a href="${createLink(controller:'member', action:'show', params:[id: memberHistory.id])}">${memberHistory.identificationNumber}</a></td>
									<td>${memberHistory.firstname} ${memberHistory.lastname}</td>
									<td>${memberHistory.balance}</td>
									<td>${memberHistory.interest}</td>
									<td>${memberHistory.telNo}</td>
									<td>${memberHistory.gender}</td>
									<td>${memberHistory.address}</td>
									<td><g:message code="member.label.status.${memberHistory.status}"></g:message></td>
								</tr>
							</g:each>
						</tbody>
					</table>

			</div>

		</div>
	</body>
</html>
