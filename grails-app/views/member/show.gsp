<html>
	<head>
		<meta name="layout" content="main">
		<title>${"${memberInstance.firstname} ${memberInstance.lastname}"}</title>
	</head>
	<body>

		<div class="container">
			<div class="row offset1">
				<h1>${"${memberInstance.firstname} ${memberInstance.lastname}"}</h1>
			</div>
			<div class="row offset2">
				<dl>
					<dt>Identification Number</dt>
					<dd>${memberInstance.identificationNumber}</dd>
					<dt>First Name</dt>
					<dd>${memberInstance.firstname}</dd>
					<dt>Last Name</dt>
					<dd>${memberInstance.lastname}</dd>
					<dt>Telephone Number</dt>
					<dd>
						<g:if test="${memberInstance.telNo}">
							${memberInstance.telNo}
						</g:if>
						<g:else>-</g:else>
					</dd>
					<dt>Gender</dt>
					<dd>${memberInstance.gender}</dd>
					<dt>Address</dt>
					<dd>
						<g:if test="${memberInstance.address}">
							<address>${memberInstance.address}</address>
						</g:if>
						<g:else>-</g:else>
					</dd>
				</dl>
			</div>
		</div>
	</body>
</html>
