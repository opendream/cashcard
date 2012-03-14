<html>
  <head>
    <meta name="layout" content="main" />
    <title>${message(code: "member.vefify.title", default: "List All Member")}</title>
  </head>
  <body>
		<div class="container">
			<header class="page-header">
				<h1>${message(code: "member.vefify.title", default: "List All Member")}</h1>
			</header>
		</div>

	  <div class="container">
      <g:form action="verifyCard" class="form-horizontal">

    		<div class="control-group ${flash.error? 'error' : ''}">
          <label for="id-number" class="control-label">${message(code: "member.label.identificationNumber", default: "Identification Number")}</label>
          <div class="controls">
            <input id="id-number" name="cardId" type="text" placeholder="${message(code: "member.placeholder.identificationNumber", default: "Identification Number")}" />
          </div>
        </div>

        <div class="form-actions">
          <button class="btn btn-primary" type="submit"><i class="icon-ok icon-white"></i> ${message(code: "member.label.verify", default: "Verify")}</button>
        </div>
  	  </g:form>
	  </div>

	</body>
</html>