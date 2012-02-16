<html>
  <head>
    <meta name="layout" content="main" />
    <title>List All Member</title>
  </head>
  <body> 
	  <div class="container">
      <g:form action="verifyCard" class="form-horizontal">

    		<div class="control-group ${flash.error? 'error' : ''}">
          <label for="id-number" class="control-label">Identification Number</label>
          <div class="controls">
            <input id="id-number" name="identificationNumber" type="text" placeholder="Card ID" />
          </div>
        </div>
        
        <div class="form-actions">
          <button class="btn btn-primary" type="submit"><i class="icon-ok icon-white"></i> Verify</button>
        </div>
  	  </g:form>
	  </div>

	</body>
</html>