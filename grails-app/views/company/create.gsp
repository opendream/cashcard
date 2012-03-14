<html>

<head>
	<meta name='layout' content='springSecurityUI'/>
	<g:set var="entityName" value="${message(code: 'company.label', default: 'Company')}"/>
	<title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>

<div class="body">

	<s2ui:form width='100%' height='250' elementId='formContainer'
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