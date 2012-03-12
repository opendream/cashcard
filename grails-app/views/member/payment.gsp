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
            ${roundUpDebt}
          </div>
        </div>
    		<div class="control-group ${flash.error? 'error' : ''}">
          <label for="amount" class="control-label">จำนวนเงินที่จะชำระ</label>
          <div class="controls">
              <g:field type="text" id="amount" name="amount" pattern="\\d*(\\.\\d\\d)?" required="" value="" />
          </div>
        </div>

        <div class="control-group ${flash.error? 'error' : ''}">
          <label for="net" class="control-label">รับเงินมา</label>
          <div class="controls">
              <g:field type="text" id="net" name="net" pattern="\\d*(\\.\\d\\d)?" required="" value="" />
          </div>
        </div>

        <div class="form-actions">
          <button class="btn btn-primary" type="submit"><i class="icon-ok icon-white"></i> Pay</button>
          <g:link action="show" id="${memberInstance?.id}">Cancel</g:link>
        </div>
  	  </g:form>
	  </div>

	</body>
</html>