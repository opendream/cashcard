<%@ page import="th.co.opendream.cashcard.Member.Gender" %>
<html>
  <head>
    <meta name="layout" content="main" />
    <title>${message(code: 'member.create.title', default: 'Register Member')}</title>
  </head>
  <body>
		<div class="container">
			<header class="page-header">
				<h1>${message(code: 'member.create.title', default: 'Register Member')}</h1>
			</header>
		</div>
    <g:hasErrors bean="${memberInstance}">
      <div id="errors" class="alert alert-error">
          <g:renderErrors bean="${memberInstance}" as="list"></g:renderErrors>
      </div><!-- /errors -->
    </g:hasErrors>
    <div class="container" >
      <g:form action="save" class="form-horizontal">
        <div class="control-group ${hasErrors(bean: memberInstance, field: 'identificationNumber', 'error')}">
          <label for="id-number" class="control-label">${message(code: 'member.label.identificationNumber', default: 'Identification Number')}</label>
          <div class="controls">
            <input id="id-number" name="identificationNumber" type="text" placeholder="${message(code: 'member.placeholder.identificationNumber', default: 'ID card number')}" value="${memberInstance?.identificationNumber}">
          </div>
        </div>

        <div class="control-group ${hasErrors(bean: memberInstance, field: 'firstname', 'error')}">
          <label for="first-name" class="control-label">${message(code: 'member.label.firstName', default: 'First Name')}</label>
          <div class="controls">
            <input id="first-name" name="firstname" type="text" placeholder="${message(code: 'member.label.firstName', default: 'First Name')}" value="${memberInstance?.firstname}">
          </div>
        </div>

        <div class="control-group  ${hasErrors(bean: memberInstance, field: 'lastname', 'error')}">
          <label class="control-label" for="last-name">${message(code: 'member.label.lastName', default: 'Last Name')}</label>
          <div class="controls">
            <input id="last-name" name="lastname" type="text" placeholder="${message(code: 'member.label.lastName', default: 'Last Name')}" value="${memberInstance?.lastname}">
          </div>
        </div>

        <div class="control-group ${hasErrors(bean: memberInstance, field: 'telNo', 'error')}">
          <label class="control-label" for="tel">${message(code: 'member.label.telNo', default: 'Tel No.')}</label>
          <div class="controls">
            <input id="tel" name="telNo" type="text" placeholder="${message(code: 'member.label.telNo', default: 'Tel No.')}" value="${memberInstance?.telNo}">
          </div>
        </div>

        <div class="control-group ${hasErrors(bean: memberInstance, field: 'gender', 'error')}">
          <label class="control-label">${message(code: 'member.label.gender', default: 'Gender')}</label>
          <div class="controls">
            <div class="btn-group" data-toggle="buttons-radio">

              <label class="btn">${message(code: 'member.label.male', default: 'Male')} <input style="display:none;" type="radio" name="gender" ${memberInstance?.gender == Gender?.MALE? 'checked': null} value="MALE"/> </label>
              <label class="btn">${message(code: 'member.label.female', default: 'Female')}<input style="display:none;" type="radio" name="gender" ${memberInstance?.gender == Gender?.FEMALE? 'checked': null} value="FEMALE"/> </label>
            </div>
          </div>
        </div>

        <div class="control-group ${hasErrors(bean: memberInstance, field: 'address', 'error')}">
          <label class="control-label" for="address">${message(code: 'member.label.address', default: 'Address')}</label>
          <div class="controls">
            <textarea id="address" name="address" rows="3" type="text" placeholder="${message(code: 'member.label.address', default: 'Address')}">${memberInstance?.address}</textarea>
          </div>
        </div>


        <div class="form-actions">
          <button class="btn btn-primary" type="submit"><i class="icon-ok icon-white"></i> ${message(code: 'member.label.register', default: 'Register')}</button>
        </div>
      </g:form>
    </div>

  </body>
</html>