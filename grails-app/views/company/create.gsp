<%@ page import="th.co.opendream.cashcard.Policy" %>

<html>

<head>
	<meta name='layout' content='springSecurityUI'/>
	<g:set var="entityName" value="${message(code: 'cashcard.cooperative.label', default: 'Cooperative')}"/>
	<title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>

<div class="body">

	<s2ui:form width='100%' height='350' elementId='formContainer'
	           titleCode='default.create.label' titleCodeArgs='[entityName]'>

	<g:form action="save" name='companyCreateForm'>
		<div class="dialog">

			<br/>

			<table>
				<tbody>

					<s2ui:textFieldRow name='name' labelCode='company.name.label' bean="${company}"
					                   size='50' labelCodeDefault='Name' value="${company?.name}"/>

					<s2ui:textFieldRow name='address' labelCode='company.address.label' bean="${conpany}"
					                   size='50' labelCodeDefault='Address' value="${company?.address}"/>

					<s2ui:textFieldRow name='taxId' labelCode='company.taxId.label' bean="${conpany}"
					                   size='50' labelCodeDefault='Tax ID' value="${company?.taxId}"/>
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

					<tr class="prop">
						<td valign="top">
							<s2ui:submitButton elementId='create' form='companyCreateForm' messageCode='default.button.create.label'/>
						</td>
					</tr>

				</tbody>
			</table>
		</div>

	</g:form>

	</s2ui:form>

</div>

<script>
$(document).ready(function() {
	$('#name').focus();
});
</script>

</body>
</html>