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
					<div class="control-label span2"><strong><g:message code="member.label.identificationNumber"></g:message></strong></div>
					<div class="offset2">
						${memberInstance.identificationNumber}
					</div>
				</div>

				<div class="row">
					<div class="control-label span2"><strong><g:message code="member.label.firstName"></g:message></strong></div>
					<div class="offset2">
						${memberInstance?.firstname} ${memberInstance?.lastname}
					</div>
				</div>

				<div class="row">
					<div class="control-label span2"><strong><g:message code="member.label.gender"></g:message></strong></div>
					<div class="offset2">
						${memberInstance?.gender == th.co.opendream.cashcard.Member.Gender.MALE ? 'ชาย' : 'หญิง'}
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
					<div class="control-label span2"><strong><g:message code="member.label.address"></g:message></strong></div>
					<div class="offset2">
						<g:if test="${memberInstance.address}">
							${memberInstance.address}
						</g:if>
						<g:else>-</g:else>
					</div>
				</div>


				<hr/>

				<div class="row">
					<div class="control-label span2"><strong>สมาชิกของ</strong></div>
					<div class="offset2">
						<g:if test="${isOrigCompany}">
						<span style="color:red">
						</g:if>
						${memberInstance?.company?.name}
						<g:if test="${isOrigCompany}">
						<span style="color:red">
						</g:if>

					</div>
				</div>

				<hr/>

				<div class="row">
					<div class="control-label span2"><strong><g:message code="member.label.creditLine"></g:message></strong></div>
					<div class="span1" style="text-align:right">
						<g:formatNumber number="${memberInstance?.getRemainingFinancialAmount()}" format="#,##0.00" />
					</div>
				</div>

				<div class="row">
					<div class="control-label span2"><strong><g:message code="member.label.balance"></g:message></strong></div>
					<div class="span1" style="text-align:right">
						<g:formatNumber number="${memberInstance?.balance}" format="#,##0.00" />
					</div>
				</div>

				<div class="row">
					<div class="control-label span2"><strong><g:message code="member.label.interest"></g:message></strong></div>
					<div class="span1" style="text-align:right">
						<g:formatNumber number="${memberInstance?.getInterest()}" format="#,##0.00" />
					</div>
				</div>

				<div class="row">
					<div class="control-label span2"><strong><g:message code="member.label.totalDebt"></g:message></strong></div>
					<div class="span1" style="text-align:right">
						<g:formatNumber number="${memberInstance?.getTotalDebt()}" format="#,##0.00" />
					</div>
				</div>

			</div>
		</body>
</html>
