<html>
  <head>
    <meta name="layout" content="main" />
    <title><g:message code="cashcard.userprofile.label"></g:message></title>
  </head>
  <body>
		<div class="container">
			<header class="page-header">
				<h1><g:message code="cashcard.userprofile.label"></g:message></h1>
			</header>
		</div>
    
    <g:if test="${flash.error}">
        <div id="errors" class="alert alert-error">
          ${flash.message}
        </div><!-- /errors -->
    </g:if>
	  <div class="container">
      <g:form action="edit"  class="form-horizontal">
        <div class="control-group ${flash.error? 'error' : ''}">
          <label for="amount" class="control-label"><g:message code="cashcard.username.label"></g:message></label>
          <div class="controls" style="padding-top:5px">${user.username}</div>
        </div>
    		
        <div class="control-group ${flash.error? 'error' : ''}">
          <label for="amount" class="control-label"><g:message code="cashcard.cooperative.label"></g:message></label>
          <div class="controls" style="padding-top:5px">${user.company.name}</div>
        </div>

        <div class="form-actions">
          <button class="btn btn-primary" type="submit"><i class="icon-edit icon-white"></i> <g:message code="default.button.edit.label"></g:message></button>
        </div>
  	  </g:form>
	  </div>

	</body>
</html>