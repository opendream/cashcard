
<%@ page import="th.co.opendream.cashcard.MemberHistory" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'memberHistory.label', default: 'MemberHistory')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-memberHistory" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-memberHistory" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list memberHistory">
			
				<g:if test="${memberHistoryInstance?.identificationNumber}">
				<li class="fieldcontain">
					<span id="identificationNumber-label" class="property-label"><g:message code="memberHistory.identificationNumber.label" default="Identification Number" /></span>
					
						<span class="property-value" aria-labelledby="identificationNumber-label"><g:fieldValue bean="${memberHistoryInstance}" field="identificationNumber"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${memberHistoryInstance?.address}">
				<li class="fieldcontain">
					<span id="address-label" class="property-label"><g:message code="memberHistory.address.label" default="Address" /></span>
					
						<span class="property-value" aria-labelledby="address-label"><g:fieldValue bean="${memberHistoryInstance}" field="address"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${memberHistoryInstance?.balance}">
				<li class="fieldcontain">
					<span id="balance-label" class="property-label"><g:message code="memberHistory.balance.label" default="Balance" /></span>
					
						<span class="property-value" aria-labelledby="balance-label"><g:fieldValue bean="${memberHistoryInstance}" field="balance"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${memberHistoryInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="memberHistory.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${memberHistoryInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${memberHistoryInstance?.firstname}">
				<li class="fieldcontain">
					<span id="firstname-label" class="property-label"><g:message code="memberHistory.firstname.label" default="Firstname" /></span>
					
						<span class="property-value" aria-labelledby="firstname-label"><g:fieldValue bean="${memberHistoryInstance}" field="firstname"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${memberHistoryInstance?.gender}">
				<li class="fieldcontain">
					<span id="gender-label" class="property-label"><g:message code="memberHistory.gender.label" default="Gender" /></span>
					
						<span class="property-value" aria-labelledby="gender-label"><g:fieldValue bean="${memberHistoryInstance}" field="gender"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${memberHistoryInstance?.interest}">
				<li class="fieldcontain">
					<span id="interest-label" class="property-label"><g:message code="memberHistory.interest.label" default="Interest" /></span>
					
						<span class="property-value" aria-labelledby="interest-label"><g:fieldValue bean="${memberHistoryInstance}" field="interest"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${memberHistoryInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="memberHistory.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${memberHistoryInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${memberHistoryInstance?.lastname}">
				<li class="fieldcontain">
					<span id="lastname-label" class="property-label"><g:message code="memberHistory.lastname.label" default="Lastname" /></span>
					
						<span class="property-value" aria-labelledby="lastname-label"><g:fieldValue bean="${memberHistoryInstance}" field="lastname"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${memberHistoryInstance?.member}">
				<li class="fieldcontain">
					<span id="member-label" class="property-label"><g:message code="memberHistory.member.label" default="Member" /></span>
					
						<span class="property-value" aria-labelledby="member-label"><g:link controller="member" action="show" id="${memberHistoryInstance?.member?.id}">${memberHistoryInstance?.member?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${memberHistoryInstance?.status}">
				<li class="fieldcontain">
					<span id="status-label" class="property-label"><g:message code="memberHistory.status.label" default="Status" /></span>
					
						<span class="property-value" aria-labelledby="status-label"><g:fieldValue bean="${memberHistoryInstance}" field="status"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${memberHistoryInstance?.telNo}">
				<li class="fieldcontain">
					<span id="telNo-label" class="property-label"><g:message code="memberHistory.telNo.label" default="Tel No" /></span>
					
						<span class="property-value" aria-labelledby="telNo-label"><g:fieldValue bean="${memberHistoryInstance}" field="telNo"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${memberHistoryInstance?.id}" />
					<g:link class="edit" action="edit" id="${memberHistoryInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
