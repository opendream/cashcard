<html>
  <head>
    <meta name="layout" content="main" />
    <title>${memberInstance} : <g:message code="member.toolbar.withdraw"></g:message></title>
  </head>
  <body>
		<div class="container">
			<header class="page-header">
				<h1>${memberInstance} : <g:message code="member.toolbar.withdraw"></g:message></h1>
			</header>
		</div>

    <g:render template="toolbar" />
    
    <g:if test="${flash.error}">
        <div id="errors" class="alert alert-error">
          ${flash.error}
        </div><!-- /errors -->
    </g:if>
	  <div class="container">
      <g:form action="withdraw" class="form-horizontal" useToken="true">
        <g:hiddenField name="id" value="${memberInstance?.id}" />

        <div class="control-group ${flash.error? 'error' : ''}">
          <label for="amount" class="control-label"><g:message code="member.label.creditLine"></g:message></label>
          <div class="controls" style="padding-top:5px">
              <g:formatNumber number="${memberInstance?.getRemainingFinancialAmount()}" format="#,##0.00" />
          </div>
        </div>

    		<div class="control-group ${flash.error? 'error' : ''}">
          <label for="amount" class="control-label"><g:message code="cashcard.withdraw.amount"></g:message></label>
          <div class="controls">
              <g:field type="text" id="amount" name="amount" pattern="\\d*(\\.\\d\\d)?" required="" value="" />
          </div>
        </div>

        <div class="form-actions">
          <button class="btn btn-primary" type="submit"><i class="icon-ok icon-white"></i> <g:message code="cashcard.button.withdraw.label"></g:message></button>
          <g:link action="show" id="${memberInstance?.id}"><g:message code="default.button.cancel.label"></g:message></g:link>
        </div>
  	  </g:form>
	  </div>

	</body>
</html>