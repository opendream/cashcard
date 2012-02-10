<%@ page import="th.co.opendream.cashcard.domain.Member.Gender" %>
<html>
  <head>
    <meta name="layout" content="main" />
    <title>Register Member</title>
  </head>
  <body> 
    <g:hasErrors bean="${memberInstance}">
      <div id="errors" class="alert alert-error">
          <g:renderErrors bean="${memberInstance}" as="list"></g:renderErrors>
      </div><!-- /errors -->
    </g:hasErrors>
    <div class="container" >
      <g:form action="save" class="form-horizontal">
        <div class="control-group ${hasErrors(bean: memberInstance, field: 'identificationNumber', 'error')}">
          <label for="id-number" class="control-label">Identification Number</label>
          <div class="controls">
            <input id="id-number" name="identificationNumber" type="text" placeholder="Personal Id" value="${memberInstance?.identificationNumber}">
          </div>
        </div>

        <div class="control-group ${hasErrors(bean: memberInstance, field: 'firstname', 'error')}">
          <label for="first-name" class="control-label">First Name</label>
          <div class="controls">
            <input id="first-name" name="firstname" type="text" placeholder="First Name" value="${memberInstance?.firstname}">
          </div>
        </div>

        <div class="control-group  ${hasErrors(bean: memberInstance, field: 'lastname', 'error')}">
          <label class="control-label" for="last-name">Last Name</label>
          <div class="controls">
            <input id="last-name" name="lastname" type="text" placeholder="Last Name" value="${memberInstance?.lastname}">
          </div>
        </div>

        <div class="control-group ${hasErrors(bean: memberInstance, field: 'telNo', 'error')}">
          <label class="control-label" for="tel">Tel.</label>
          <div class="controls">
            <input id="tel" name="telNo" type="text" placeholder="Tel." value="${memberInstance?.telNo}">
          </div>
        </div>

        <div class="control-group ${hasErrors(bean: memberInstance, field: 'gender', 'error')}">
          <label class="control-label">Gender</label>
          <div class="controls">
            <div class="btn-group" data-toggle="buttons-radio">
              
              <label class="btn">Male <input type="radio" name="gender" ${memberInstance?.gender == Gender?.MALE? 'checked': null} value="MALE"/> </label>
              <label class="btn">Female <input type="radio" name="gender" ${memberInstance?.gender == Gender?.FEMALE? 'checked': null} value="FEMALE"/> </label>
            </div>
          </div>
        </div>

        <div class="control-group ${hasErrors(bean: memberInstance, field: 'address', 'error')}">
          <label class="control-label" for="address">Address</label>
          <div class="controls">
            <textarea id="address" name="address" type="text" placeholder="Address">${memberInstance?.address}</textarea>
          </div>
        </div>


        <div class="form-actions">
          <button class="btn btn-primary" type="submit"><i class="icon-ok icon-white"></i> Register</button>
        </div>
      </g:form>
    </div>

  </body>
</html>