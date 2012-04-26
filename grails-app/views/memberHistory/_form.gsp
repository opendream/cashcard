<%@ page import="th.co.opendream.cashcard.MemberHistory" %>



<div class="fieldcontain ${hasErrors(bean: memberHistoryInstance, field: 'identificationNumber', 'error')} required">
	<label for="identificationNumber">
		<g:message code="memberHistory.identificationNumber.label" default="Identification Number" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="identificationNumber" pattern="${memberHistoryInstance.constraints.identificationNumber.matches}" required="" value="${memberHistoryInstance?.identificationNumber}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberHistoryInstance, field: 'address', 'error')} ">
	<label for="address">
		<g:message code="memberHistory.address.label" default="Address" />
		
	</label>
	<g:textField name="address" value="${memberHistoryInstance?.address}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberHistoryInstance, field: 'balance', 'error')} required">
	<label for="balance">
		<g:message code="memberHistory.balance.label" default="Balance" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="balance" required="" value="${fieldValue(bean: memberHistoryInstance, field: 'balance')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberHistoryInstance, field: 'firstname', 'error')} ">
	<label for="firstname">
		<g:message code="memberHistory.firstname.label" default="Firstname" />
		
	</label>
	<g:textField name="firstname" value="${memberHistoryInstance?.firstname}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberHistoryInstance, field: 'gender', 'error')} required">
	<label for="gender">
		<g:message code="memberHistory.gender.label" default="Gender" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="gender" from="${th.co.opendream.cashcard.Member$Gender?.values()}" keys="${th.co.opendream.cashcard.Member$Gender.values()*.name()}" required="" value="${memberHistoryInstance?.gender?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberHistoryInstance, field: 'interest', 'error')} required">
	<label for="interest">
		<g:message code="memberHistory.interest.label" default="Interest" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="interest" required="" value="${fieldValue(bean: memberHistoryInstance, field: 'interest')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberHistoryInstance, field: 'lastname', 'error')} ">
	<label for="lastname">
		<g:message code="memberHistory.lastname.label" default="Lastname" />
		
	</label>
	<g:textField name="lastname" value="${memberHistoryInstance?.lastname}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberHistoryInstance, field: 'member', 'error')} required">
	<label for="member">
		<g:message code="memberHistory.member.label" default="Member" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="member" name="member.id" from="${th.co.opendream.cashcard.Member.list()}" optionKey="id" required="" value="${memberHistoryInstance?.member?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberHistoryInstance, field: 'status', 'error')} required">
	<label for="status">
		<g:message code="memberHistory.status.label" default="Status" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="status" from="${th.co.opendream.cashcard.Member$Status?.values()}" keys="${th.co.opendream.cashcard.Member$Status.values()*.name()}" required="" value="${memberHistoryInstance?.status?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberHistoryInstance, field: 'telNo', 'error')} ">
	<label for="telNo">
		<g:message code="memberHistory.telNo.label" default="Tel No" />
		
	</label>
	<g:textField name="telNo" value="${memberHistoryInstance?.telNo}"/>
</div>

