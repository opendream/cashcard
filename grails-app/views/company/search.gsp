<html>

<head>
	<meta name='layout' content='springSecurityUI'/>
	<title><g:message code='spring.security.ui.company.search' default='Company Search'/></title>
</head>

<body>

<div>

	<s2ui:form width='100%' height='250' elementId='formContainer'
	           titleCode='spring.security.ui.company.search'>

	<g:form action='companySearch' name='companySearchForm'>

		<br/>

		<table>
			<tbody>
			<tr>
				<td><g:message code='company.name.label' default='Name'/>:</td>
				<td><g:textField name='name' class='textField' size='50' maxlength='255' autocomplete='off' value='${name}'/></td>
			</tr>
			<tr>
				<td><g:message code='company.address.label' default='Address'/>:</td>
				<td><g:textField name='address' class='textField' size='50' maxlength='255' autocomplete='off' value='${address}'/></td>
			</tr>
			<tr>
				<td><g:message code='company.taxId.label' default='Tax ID'/>:</td>
				<td><g:textField name='taxId' class='textField' size='50' maxlength='255' autocomplete='off' value='${taxId}'/></td>
			</tr>
			<tr><td colspan='2'>&nbsp;</td></tr>
			<tr>
				<td colspan='2'><s2ui:submitButton elementId='search' form='companySearchForm' messageCode='spring.security.ui.search'/></td>
			</tr>
			</tbody>
		</table>
	</g:form>

	</s2ui:form>

	<g:if test='${searched}'>

<%
def queryParams = [name: name, address: address, taxId: taxId]
%>

	<div class="list">
	<table>
		<thead>
		<tr>
			<g:sortableColumn property="name" title="${message(code: 'company.name.label', default: 'name')}" params="${queryParams}"/>
			<g:sortableColumn property="address" title="${message(code: 'company.address.label', default: 'address')}" params="${queryParams}"/>
			<g:sortableColumn property="taxId" title="${message(code: 'company.taxId.label', default: 'taxId')}" params="${queryParams}"/>
		</tr>
		</thead>

		<tbody>
		<g:each in="${results}" status="i" var="company">
		<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			<td><g:link action="edit" id="${company.id}">${fieldValue(bean: company, field: "name")}</g:link></td>
			<td>${fieldValue(bean: company, field: "address")}</td>
			<td>${fieldValue(bean: company, field: "taxId")}</td>
		</tr>
		</g:each>
		</tbody>
	</table>
	</div>

	<div class="paginateButtons">
		<g:paginate total="${totalCount}" params="${queryParams}" />
	</div>

	<div style="text-align:center">
		<s2ui:paginationSummary total="${totalCount}"/>
	</div>

	</g:if>

</div>

<script>
$(document).ready(function() {
	$("#name").focus().autocomplete({
		minLength: 2,
		cache: false,
		source: "${createLink(action: 'ajaxCompanySearch')}"
	});
});
</script>

</body>
</html>