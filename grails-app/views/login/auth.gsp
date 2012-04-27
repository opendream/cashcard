<!doctype html>

<r:require modules="bootstrap"/>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Cashcard</title>
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Le styles -->
    <r:layoutResources />
    
    <style type="text/css">
      /* Override some defaults */
      html, body {
        background-color: #eee;
      }
      body {
        padding-top: 40px; /* 40px to make the container go all the way to the bottom of the topbar */
      }
      .container > footer p {
        text-align: center; /* center align it with the container */
      }


      /* The white background content wrapper */
      .container > .content {
        background-color: #fff;
        padding: 20px;
        margin: 0 -20px; /* negative indent the amount of the padding to maintain the grid system */
        -webkit-border-radius: 0 0 6px 6px;
           -moz-border-radius: 0 0 6px 6px;
                border-radius: 0 0 6px 6px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.15);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.15);
                box-shadow: 0 1px 2px rgba(0,0,0,.15);
      }

      /* Page header tweaks */
      .page-header {
        background-color: #f5f5f5;
        padding: 20px 20px 10px;
        margin: -20px -20px 20px;
      }

      /* Styles you shouldn't keep as they are for displaying this base example only */
      .content .span10,
      .content .span4 {
        min-height: 500px;
      }
      /* Give a quick and non-cross-browser friendly divider */
      .content .span4 {
        margin-left: 0;
        padding-left: 19px;
        border-left: 1px solid #eee;
      }

      .topbar .btn {
        border: 0;
      }

    </style>

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" type="image/x-icon" href="${resource(dir:'images',file:'favicon2.ico')}"/>
    <link rel="apple-touch-icon" href="images/apple-touch-icon.png">
    <link rel="apple-touch-icon" sizes="72x72" href="images/apple-touch-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="114x114" href="images/apple-touch-icon-114x114.png">
  </head>

  <body>

    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="brand" href="#"><g:message code="project.name"></g:message></a>
          <ul class="nav">
            <li><a href="#"></a></li>
            <li><a href="./about-app.html"></a></li>
            <li><a href="#contact"></a></li>
          </ul>
          <p class="pull-right"><a href="#"></a>
          </p>
        </div>
      </div>
    </div>

	<div class="container">
		<div class="row show-grid" title="Half and half">
			
			<div>
				<div class="well span4 offset4">
			<form action='${postUrl}' method='POST' id='login'>
				<fieldset>
					<legend>Login Form</legend>
						<div class="clearfix">
							<label for="xlInput">Username</label>
								<div class="input">
									<input id="xlInput" class="span3" type="text" size="10" name="j_username">
								</div>
						</div>
						<div class="clearfix">
							<label for="xlInput">Password</label>
								<div class="input">
									<input id="xlInput" class="span3" type="password" size="10" name="j_password">
									<g:hiddenField name="${rememberMeParameter}" id='remember_me' value="true"/>
								</div>
						</div>
						<div class="clearfix">
							<div class="input">
							<div class="inline-inputs">	
						<button id="login-button" class="btn primary" type="submit">Login</button>
						<button class="btn" type="reset">Cancel</button>
						</div>
						</div>
						</div>
												
				</fieldset>							
			</form>       
				</div>
			</div>
		</div>

      <footer>
        <p>Cashcard is developed by Opendream Co., Ltd., Bangkok, Thailand based ICT social enterprise.


      </footer>

	</div>

  </body>
</html>