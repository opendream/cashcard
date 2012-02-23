<%@ page import="th.co.opendream.cashcard.domain.InterestRate" %>
	<div class="control-group ${hasErrors(bean: interestRateInstance, field: 'startDate', 'error')} required">
		<label for="startDate" class="control-label">
			<g:message code="interestRate.startDate.label" default="Start Date" />
			<span class="required-indicator">*</span>
		</label>
		<div class="controls">
			<g:datePicker name="startDate" precision="day"  value="${interestRateInstance?.startDate}"  />
		</div>
	</div>

	<div class="control-group ${hasErrors(bean: interestRateInstance, field: 'rate', 'error')} required">
		<label for="rate" class="control-label">
			<g:message code="interestRate.rate.label" default="Rate" />
			<span class="required-indicator">*</span>
		</label>
		<div class="controls">
			<g:field type="text" name="rate" pattern="\\d*\\.\\d\\d" required="" value="${formatNumber (type:'number', number:interestRateInstance.rate, maxFractionDigits: '2', minFractionDigits : '2')}" />
		</div>
	</div>