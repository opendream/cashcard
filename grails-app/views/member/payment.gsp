<html>
  <head>
    <meta name="layout" content="main" />
    <title>${memberInstance} : <g:message code="member.toolbar.pay"></g:message></title>
  </head>
  <body>
		<div class="container">
			<header class="page-header">
				<h1>${memberInstance} : <g:message code="member.toolbar.pay"></g:message></h1>
			</header>
		</div>

    <g:render template="toolbar" />

    <g:if test="${flash.error}">
        <div id="errors" class="alert alert-error">
          ${flash.error}
        </div><!-- /errors -->
    </g:if>
	  <div class="container">
      <g:form action="pay" class="form-horizontal" useToken="true">
        <g:hiddenField name="id" value="${memberInstance?.id}" />

        <!-- -->
        <div class="control-group">
          <label class="control-label"><strong>เงินต้นค้างชำระ</strong></label>
          <div class="offset2">
            <g:formatNumber number="${memberInstance?.getRealBalance()}" format="#,##0.00" />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label"><strong>ดอกเบี้ย</strong></label>
          <div class="offset2">
            <g:formatNumber number="${memberInstance?.getInterest()}" format="#,##0.00" />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label"><strong>รวมยอดคงค้าง</strong></label>
          <div class="offset2">
            <g:formatNumber number="${memberInstance?.getTotalDebt()}" format="#,##0.00" />
          </div>
        </div>
        <!-- -->

        <div class="control-group">
          <label class="control-label"><strong>คิดเป็นเงินที่ต้องจ่าย (ปัดเศษแล้ว)</strong></label>
          <div class="offset2" style="padding-top:5px">
            ${roundUpDebt}
          </div>
        </div>
    		<div class="control-group ${flash.error? 'error' : ''}">
          <label for="amount" class="control-label">จำนวนเงินที่จะชำระ<br />ในครั้งนี้</label>
          <div class="controls">
              <g:field type="text" id="amount" name="amount" pattern="\\d*(\\.\\d\\d)?" required="" value="${amount}" />
          </div>
        </div>

        <div class="control-group ${flash.error? 'error' : ''}">
          <label for="net" class="control-label"><g:message code="cashcard.recieve.amount"></g:message></label>
          <div class="controls">
              <g:field type="text" id="net" name="net" pattern="\\d*(\\.\\d\\d)?" required="" value="${net}" />
              <p class="help-block">ระบบจะคำนวณเงินทอนให้</p>
          </div>
        </div>

        <div class="form-actions">
          <button class="btn btn-primary" type="submit"><i class="icon-ok icon-white"></i> <g:message code="cashcard.button.pay.label"></g:message></button>
          <g:link action="show" id="${memberInstance?.id}"><g:message code="default.button.cancel.label"></g:message></g:link>
        </div>
  	  </g:form>
	  </div>

	</body>
</html>