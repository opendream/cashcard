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
				<g:render template="toolbar" />

				<div class="row">
					<div class="control-label span2"><strong>First Name</strong></div>
					<div class="offset2">
						${memberInstance?.firstname}
					</div>
				</div>

				<div class="row">
					<div class="control-label span2"><strong>Last Name</strong></div>
					<div class="offset2">
						${memberInstance?.lastname}
					</div>
				</div>

				<div class="row">
					<div class="control-label span2"><strong>Telephone Number</strong></div>
					<div class="offset2">
						<g:if test="${memberInstance.telNo}">
							${memberInstance.telNo}
						</g:if>
						<g:else>-</g:else>
					</div>
				</div>

				<div class="row">
					<div class="control-label span2"><strong>Gender</strong></div>
					<div class="offset2">
						${memberInstance?.gender}
					</div>
				</div>

				<div class="row">
					<div class="control-label span2"><strong>Address</strong></div>
					<div class="offset2">
						<g:if test="${memberInstance.address}">
							${memberInstance.address}
						</g:if>
						<g:else>-</g:else>
					</div>
				</div>

				<div class="row">
					<div class="control-label span2"><strong>Credit Line</strong></div>
					<div class="offset2">
						<g:formatNumber number="${memberInstance?.getRemainingFinancialAmount()}" type="currency" currencyCode="THB" />
					</div>
				</div>

				<div class="row">
					<div class="control-label span2"><strong>Balance</strong></div>
					<div class="offset2">
						<g:formatNumber number="${memberInstance?.balance}" type="currency" currencyCode="THB" />
					</div>
				</div>

				<div class="row">
					<div class="control-label span2"><strong>Interest</strong></div>
					<div class="offset2">
						<g:formatNumber number="${memberInstance?.getInterest()}" type="currency" currencyCode="THB" />
					</div>
				</div>

				<div class="row">
					<div class="control-label span2"><strong>Total Debt</strong></div>
					<div class="offset2">
						<g:formatNumber number="${memberInstance?.getTotalDebt()}" type="currency" currencyCode="THB" />
					</div>
				</div>

			</div>
		</body>
</html>
