<html>
	<head>
		<meta name="layout" content="main">
		<title>${"${memberInstance.firstname} ${memberInstance.lastname}"}</title>
	</head>
	<body>
		<div class="container">
			<header class="page-header">
				<h1>${"${memberInstance.firstname} ${memberInstance.lastname}"}</h1>
			</header>
		</div>

		<div class="container">
					<div class="btn-group">
					  <a class="btn" href="#">Deposit</a>
					  <a class="btn" href="${createLink(controller:'member', action:'withdraw', params:[uid:memberInstance.id])}">Withdraw</a>
					</div>

				<div class="control-label span2"><strong>First Name</strong></div>
				<div class="offset2">
					${memberInstance?.firstname}
				</div>

				<div class="control-label span2"><strong>Last Name</strong></div>
				<div class="offset2">
					${memberInstance?.lastname}
				</div>

				<div class="control-label span2"><strong>Telephone Number</strong></div>
				<div class="offset2">
					<g:if test="${memberInstance.telNo}">
						${memberInstance.telNo}
					</g:if>
					<g:else>-</g:else>
				</div>

				<div class="control-label span2"><strong>Gender</strong></div>
				<div class="offset2">
					${memberInstance?.gender}
				</div>

				<div class="control-label span2"><strong>Address</strong></div>
				<div class="offset2">
					<g:if test="${memberInstance.address}">
						${memberInstance.address}
					</g:if>
					<g:else>-</g:else>
				</div>

				<div class="control-label span2"><strong>Balance</strong></div>
				<div class="offset2">
					<g:formatNumber number="${memberInstance?.balance}" type="currency" currencyCode="THB" />
				</div>

		</div>
	</body>
</html>
