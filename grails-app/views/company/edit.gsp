<%@ page import="th.co.opendream.cashcard.Policy" %>
<html>

<head>
	<meta name='layout' content='springSecurityUI'/>
	<g:set var="entityName" value="${message(code: 'company.label', default: 'Company')}"/>
	<title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>

<h3><g:message code="default.edit.label" args="[entityName]"/></h3>

<g:form action="update" name='companyEditForm'>
<g:hiddenField name="id" value="${company?.id}"/>
<g:hiddenField name="version" value="${company?.version}"/>

<%
def tabData = []
tabData << [name: 'info', icon: 'icon_role',  messageCode: 'spring.security.ui.company.info']
tabData << [name: 'users',    icon: 'icon_users', messageCode: 'spring.security.ui.company.users']
tabData << [name: 'members',    icon: 'icon_users', messageCode: 'spring.security.ui.company.members']
%>

<s2ui:tabs elementId='tabs' height='300' data="${tabData}">

	<s2ui:tab name='info' height='250'>
		<table>
		<tbody>
			<s2ui:textFieldRow name='name' labelCode='company.name.label' bean="${company}"
                            labelCodeDefault='Name' value="${company?.name}"/>
            <s2ui:textFieldRow name='address' labelCode='company.address.label' bean="${company}"
                            labelCodeDefault='Address' value="${company?.address}"/>
            <s2ui:textFieldRow name='taxId' labelCode='company.taxId.label' bean="${company}"
                            labelCodeDefault='Tax ID' value="${company?.taxId}"/>
            <tr class="prop">
						<td class="name" valign="top">
							<label for="policyKeyInterestMethod"><g:message code="company.policyKeyInterestMethod.label" default="Key Interest Method" /></label>
						</td>
						<td class="value " valign="top">
							
							<g:select name="policyKeyInterestMethod" value="${policyKeyInterestMethod}" optionKey="id" optionValue="key"
							from="${[[id:Policy.VALUE_COMPOUND, key:message(code: 'policy.CompoundInterest.label', default: 'Compound Interest')], 
							[id:Policy.VALUE_NON_COMPOUND, key:message(code: 'policy.NonCompoundInterest.label', default: 'Non Compound Interest')]]}" 
							 />				
						</td>
					</tr>
					
					<tr class="prop">
						<td class="name" valign="top">
							<label for="policyKeyInterestRateLimit"><g:message code="company.policyKeyInterestRateLimit.label" default="Key Interest Rate Limit" /></label>
						</td>
						<td class="value " valign="top">
							<input id="policyKeyInterestRateLimit" type="text" size="25" value="${policyKeyInterestRateLimit}" name="policyKeyInterestRateLimit">
						</td>
					</tr>
					
					<tr class="prop">
						<td class="name" valign="top">
							<label for="policyKeyCreditLine"><g:message code="company.policyKeyCreditLine.label" default="Key Credit Line" /></label>
						</td>
						<td class="value " valign="top">
							<input id="policyKeyCreditLine" type="text" size="25" value="${policyKeyCreditLine}" name="policyKeyCreditLine">
						</td>
					</tr>
					<tr><td>&nbsp;</td></tr>
		</tbody>
		</table>
	</s2ui:tab>

	<s2ui:tab name='users' height='200'>
		<g:if test='${users?.empty}'>
		<g:message code="spring.security.ui.company_no_users"/>
		</g:if>
		<g:each var="u" in="${users}">
			<g:link controller='user' action='edit' id='${u.id}'>${u.username.encodeAsHTML()}</g:link><br/>
		</g:each>
	</s2ui:tab>

	<s2ui:tab name='members' height='200'>
		<g:if test='${members?.empty}'>
		<g:message code="spring.security.ui.company_no_members"/>
		</g:if>
		<g:each var="m" in="${members}">
			<g:link controller='member' action='edit' id='${m.id}'>${m.firstname.encodeAsHTML()} ${m.lastname.encodeAsHTML()}</g:link><br/>
		</g:each>
	</s2ui:tab>

</s2ui:tabs>

<div style='float:left; margin-top: 10px;'>
<s2ui:submitButton elementId='update' form='companyEditForm' messageCode='default.button.update.label'/>

<g:if test='${company}'>
<s2ui:deleteButton />
</g:if>

</div>

</g:form>

<g:if test='${company}'>
<s2ui:deleteButtonForm instanceId='${company.id}'/>
</g:if>

</body>
</html>