<html>
  <head>
    <meta name="layout" content="main" />
    <title>List All Member</title>
  </head>
  <body>
		<div class="container">
			<header class="page-header">
				<h1>List Members</h1>
			</header>
		</div>

		<div class="container">
			<g:form action="list" class="form-horizontal">

				<div class="control-group ${hasErrors(bean: memberInstance, field: 'identificationNumber', 'error')}">
		          <label for="id-number" class="control-label">Identification Number</label>
		          <div class="controls">
		            <input id="id-number" name="identificationNumber" type="text" placeholder="Personal Id" value="${memberInstance?.identificationNumber}">
		          </div>
		        </div>

		        <div class="control-group ${hasErrors(bean: memberInstance, field: 'firstname', 'error')}">
		          <label for="first-name" class="control-label">First Name</label>
		          <div class="controls">
		            <input id="first-name" name="firstname" type="text" placeholder="First Name" value="${memberInstance?.firstname}">
		          </div>
		        </div>

		        <div class="control-group  ${hasErrors(bean: memberInstance, field: 'lastname', 'error')}">
		          <label class="control-label" for="last-name">Last Name</label>
		          <div class="controls">
		            <input id="last-name" name="lastname" type="text" placeholder="Last Name" value="${memberInstance?.lastname}">
		          </div>
		        </div>

		        <div class="control-group ${hasErrors(bean: memberInstance, field: 'telNo', 'error')}">
		          <label class="control-label" for="tel">Tel.</label>
		          <div class="controls">
		            <input id="tel" name="telNo" type="text" placeholder="Tel." value="${memberInstance?.telNo}">
		          </div>
		        </div>

		        <div class="form-actions">
		          <button class="btn btn-primary" type="submit"><i class="icon-search icon-white"></i> Search</button>
		        </div>
			</g:form>
		</div>
		
	  <div class="container">
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>#</th>
						<th>Name</th>
						<th>Tel.</th>
						<th>Gender</th>
						<th>Address</th>
					</tr>
				</thead>
				<tbody>
					<g:each var="member" in ="${memberList}">
						<tr>
							<td>${member.identificationNumber}</td>
							<td><a href="${createLink(controller:'member', action:'show', params:[id: member.id])}">${member.firstname} ${member.lastname}</a></td>
							<td>${member.telNo}</td>
							<td>${member.gender}</td>
							<td>${member.address}</td>
						</tr>
					</g:each>
				</tbody>
			</table>

			<div class="pagination">
				<cashcard:paginate controller="member" action="list" total="${memberCount}" />
			</div>
	  </div>

	</body>
</html>