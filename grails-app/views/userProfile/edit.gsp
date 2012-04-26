<html>
  <head>
    <meta name="layout" content="main" />
    <title><g:message code="cashcard.edituserprofile.label"></g:message></title>
  </head>
  <body>
		<div class="container">
			<header class="page-header">
				<h1><g:message code="cashcard.edituserprofile.label"></g:message></h1>
			</header>
		</div>
    
    <g:if test="${flash.error}">
        <div id="errors" class="alert alert-error">
          ${flash.error}
        </div><!-- /errors -->
    </g:if>
	  <div class="container">
      <g:form action="update" class="form-horizontal">
        
        <div class="control-group ${flash.error? 'error' : ''}">
          <label for="amount" class="control-label"><g:message code="cashcard.username.label"></g:message></label>
          <div class="controls" style="padding-top:5px">${user.username}</div>
        </div>

    		<div class="control-group ${flash.error? 'error' : ''}">
          <label for="amount" class="control-label"><g:message code="cashcard.password.label"></g:message></label>
          <div class="controls">
              <g:field type="password" id="amount" name="password" required="" value="${user.password}" />
          </div>
        </div>

        <div class="control-group ${flash.error? 'error' : ''}">
          <label for="amount" class="control-label"><g:message code="cashcard.cooperative.label"></g:message></label>
          <div class="controls" style="padding-top:5px">${user.company.name}</div>
        </div>

        <div class="form-actions">          
          <button class="btn btn-primary" type="submit"><i class="icon-ok icon-white"></i> <g:message code="default.button.update.label"></g:message></button> 
          <g:link controller="userProfile" action="show" ><g:message code="default.button.cancel.label" default="Cancel"/></g:link>         
        </div>
  	  </g:form>
	  </div>

	</body>
</html>