<html>
  <head>
    <meta name="layout" content="main" />
    <title>${memberInstance} : Pay</title>
  </head>
  <body>
		<div class="container">
			<header class="page-header">
				<h1>${memberInstance} : Pay</h1>
			</header>
		</div>
    <g:if test="${flash.error}">
        <div id="errors" class="alert alert-error">
          ${flash.error}
        </div><!-- /errors -->
    </g:if>
	  <div class="container">
      <g:form action="pay" class="form-horizontal">
        <g:hiddenField name="id" value="${memberInstance?.id}" />
        <div class="control-group">
          <label class="control-label"><strong>Total Debt</strong></label>
          <div class="offset2">
            ${totalDebt}
          </div>
        </div>
    		<div class="control-group ${flash.error? 'error' : ''}">
          <label for="amount" class="control-label">Amount</label>
          <div class="controls">
              <g:field type="text" id="amount" name="amount" pattern="\\d*(\\.\\d\\d)?" required="" value="" />
          </div>
        </div>

        <div class="form-actions">
          <button class="btn btn-primary" type="submit"><i class="icon-ok icon-white"></i> Pay</button>
        </div>
  	  </g:form>
	  </div>

	</body>
</html>