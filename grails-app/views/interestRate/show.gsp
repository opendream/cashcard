<%@ page import="th.co.opendream.cashcard.InterestRate" %>
<!doctype html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'interestRate.label', default: 'InterestRate')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#show-interestRate" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-interestRate" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <ol class="property-list interestRate">

                <g:if test="${interestRateInstance?.startDate}">
                <li class="fieldcontain">
                    <span id="startDate-label" class="property-label"><g:message code="interestRate.startDate.label" default="Start Date" /></span>

                        <span class="property-value" aria-labelledby="startDate-label"><g:formatDate date="${interestRateInstance?.startDate}" /></span>

                </li>
                </g:if>

                <g:if test="${interestRateInstance?.endDate}">
                <li class="fieldcontain">
                    <span id="endDate-label" class="property-label"><g:message code="interestRate.endDate.label" default="End Date" /></span>

                        <span class="property-value" aria-labelledby="endDate-label"><g:formatDate date="${interestRateInstance?.endDate}" /></span>

                </li>
                </g:if>

                <g:if test="${interestRateInstance?.rate}">
                <li class="fieldcontain">
                    <span id="rate-label" class="property-label"><g:message code="interestRate.rate.label" default="Rate" /></span>

                        <span class="property-value" aria-labelledby="rate-label"><g:fieldValue bean="${interestRateInstance}" field="rate"/></span>

                </li>
                </g:if>

                <g:if test="${interestRateInstance?.dateCreated}">
                <li class="fieldcontain">
                    <span id="dateCreated-label" class="property-label"><g:message code="interestRate.dateCreated.label" default="Date Created" /></span>

                        <span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${interestRateInstance?.dateCreated}" /></span>

                </li>
                </g:if>

                <g:if test="${interestRateInstance?.lastUpdated}">
                <li class="fieldcontain">
                    <span id="lastUpdated-label" class="property-label"><g:message code="interestRate.lastUpdated.label" default="Last Updated" /></span>

                        <span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${interestRateInstance?.lastUpdated}" /></span>

                </li>
                </g:if>

            </ol>
            <g:form>
                <fieldset class="buttons">
                    <g:hiddenField name="id" value="${interestRateInstance?.id}" />
                    <g:link class="edit" action="edit" id="${interestRateInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
