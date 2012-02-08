<html>
  <head>
    <meta name="layout" content="main" />
    <title>Register Member</title>
  </head>
  <body> 
    <div class="container" >
      
      
        <form class="form-horizontal">

          <div class="control-group">
            <label for="first-name" class="control-label">First Name</label>
            <div class="controls">
              <input id="first-name" type="text" placeholder="First Name" class="span5">
            </div>
          </div>

          <div class="control-group">
            <label class="control-label" for="last-name">Last Name</label>
            <div class="controls">
              <input id="last-name" type="text" placeholder="Last Name">
            </div>
          </div>

          <div class="control-group">
            <label class="control-label" for="tel">Tel.</label>
            <div class="controls">
              <input id="tel" type="text" placeholder="Tel.">
            </div>
          </div>

          <div class="control-group">
            <label class="control-label">Gender</label>
            <div class="controls">
              <div class="btn-group" data-toggle="buttons-radio">
                <label class="btn">Male <input type="radio" name="gender" value="male"/> </label>
                <label class="btn">Female <input type="radio" name="gender" value="female"/> </label>
              </div>
            </div>
          </div>

          <div class="control-group">
            <label class="control-label" for="address">Address</label>
            <div class="controls">
              <textarea id="address" type="text" placeholder="Address"> </textarea>
            </div>
          </div>


          <div class="form-actions">
            <button class="btn btn-primary" type="submit"><i class="icon-ok icon-white"></i> Register</button>
          </div>

       </form>
      
    </div>
    <script type="text/javascript" charset="utf-8">
      $(function() {
        $('.tabs').button();
      })
    </script>
  </body>
</html>