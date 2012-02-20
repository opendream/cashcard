<html>
  <head>
    <meta name="layout" content="main" />
    <title>Withdraw</title>
  </head>
  <body>
		<div class="container">
			<header class="page-header">
				<h1>Withdraw</h1>
			</header>
		</div>
    <g:if test="${flash.error}">
        <div id="errors" class="alert alert-error">
          ${flash.error}
        </div><!-- /errors -->
    </g:if>
	  <div class="container">
      <g:form action="withdraw" class="form-horizontal">
        <g:hiddenField name="uid" value="${memberInstance?.id}" />
    		<div class="control-group ${flash.error? 'error' : ''}">
          <label for="amount" class="control-label">Amount</label>
          <div class="controls">
            <input id="amount" name="amount" type="text" placeholder="Amount to withdraw" />
          </div>
        </div>

        <div class="form-actions">
          <button class="btn btn-primary" type="submit"><i class="icon-ok icon-white"></i> Withdraw</button>
        </div>
  	  </g:form>
	  </div>

	</body>
</html>