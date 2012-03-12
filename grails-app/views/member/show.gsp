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
				<g:if test="${flash.message}">
					<div class="message alert alert-success" role="status">${flash.message}</div>
				</g:if>
				<g:render template="toolbar" />

				<div class="row">
					<div class="control-label span2"><strong><g:message code="member.label.firstName"></g:message></strong></div>
					<div class="offset2">
						${memberInstance?.firstname}
					</div>
				</div>

				<div class="row">
					<div class="control-label span2"><strong><g:message code="member.label.lastName"></g:message></strong></div>
					<div class="offset2">
						${memberInstance?.lastname}
					</div>
				</div>

				<div class="row">
					<div class="control-label span2"><strong><g:message code="member.label.telNo"></g:message></strong></div>
					<div class="offset2">
						<g:if test="${memberInstance.telNo}">
							${memberInstance.telNo}
						</g:if>
						<g:else>-</g:else>
					</div>
				</div>

				<div class="row">
					<div class="control-label span2"><strong><g:message code="member.label.gender"></g:message></strong></div>
					<div class="offset2">
						${memberInstance?.gender}
					</div>
				</div>

				<div class="row">
					<div class="control-label span2"><strong><g:message code="member.label.address"></g:message></strong></div>
					<div class="offset2">
						<g:if test="${memberInstance.address}">
							${memberInstance.address}
						</g:if>
						<g:else>-</g:else>
					</div>
				</div>

				<div class="row">
					<div class="control-label span2"><strong><g:message code="member.label.creditLine"></g:message></strong></div>
					<div class="offset2">
						<g:formatNumber number="${memberInstance?.getRemainingFinancialAmount()}" type="currency" currencyCode="THB" />
					</div>
				</div>

				<div class="row">
					<div class="control-label span2"><strong><g:message code="member.label.balance"></g:message></strong></div>
					<div class="offset2">
						<g:formatNumber number="${memberInstance?.balance}" type="currency" currencyCode="THB" />
					</div>
				</div>

				<div class="row">
					<div class="control-label span2"><strong><g:message code="member.label.interest"></g:message></strong></div>
					<div class="offset2">
						<g:formatNumber number="${memberInstance?.getInterest()}" type="currency" currencyCode="THB" />
					</div>
				</div>

				<div class="row">
					<div class="control-label span2"><strong><g:message code="member.label.totalDebt"></g:message></strong></div>
					<div class="offset2">
						<g:formatNumber number="${memberInstance?.getTotalDebt()}" type="currency" currencyCode="THB" />
					</div>
				</div>

			</div>
		</body>
</html>
