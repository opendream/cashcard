<html>
  <head>
    <meta name="layout" content="main" />
    <title>List All Member</title>
  </head>
  <body> 
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
							<td>${member.firstname} ${member.lastname}</td>
							<td>${member.telNo}</td>
							<td>${member.gender}</td>
							<td>${member.address}</td>
						</tr>
					</g:each>
				</tbody>
			</table>
	  </div>

	</body>
</html>