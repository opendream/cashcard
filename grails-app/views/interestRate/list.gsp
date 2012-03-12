<%@ page import="th.co.opendream.cashcard.InterestRate" %>
<!doctype html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'interestRate.label', default: 'InterestRate')}" />
        <title><g:message code="interestRate.list.title"></g:message></title>
        <style type="text/css">
            td.right {
                text-align: right;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <header class="page-header">
                <h1><g:message code="interestRate.list.title"></g:message></h1>
            </header>
        </div>

        <g:render template="toolbar" />

        <div id="list-interestRate" class="content scaffold-list" role="main">
            <g:if test="${flash.message}">
                <div class="message alert alert-success" role="status">${flash.message} <a class="close" data-dismiss="alert">×</a></div>
            </g:if>
            <div class="container">
            <table class="table table-striped table-bordered table-condensed">
                <thead>
                    <tr>
                        <th class="date">${message(code: 'interestRate.startDate.label', default: 'Start Date')}</th>
                        <th class="number"> ${message(code: 'interestRate.rate.label', default: 'Rate (%)')}</th>
                        <th class="date"> ${message(code: 'interestRate.dateCreated.label', default: 'Date Created')}</th>
                        <th class="date"> ${message(code: 'interestRate.lastUpdated.label', default: 'Last Updated')}</th>
                        <th class="date" colspan="2"> ${message(code: 'default.button.action.label', default: 'Action')}</th>
                    </tr>
                </thead>
                <tbody>
                <g:each in="${interestRateInstanceList}" status="i" var="interestRateInstance">
                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                        <td class="date"><g:formatDate format="yyyy-MM-dd" date="${interestRateInstance.startDate}" /></td>
                        <td class="number"><g:formatNumber type="number" number="${interestRateInstance.rate}" maxFractionDigits="2" minFractionDigits="2" /></td>
                        <td class="date"><g:formatDate format="yyyy-MM-dd HH:mm" date="${interestRateInstance.dateCreated}" /></td>
                        <td class="date"><g:formatDate format="yyyy-MM-dd HH:mm" date="${interestRateInstance.lastUpdated}" /></td>
                        <td class="action">
                            <g:link action="edit" id="${interestRateInstance.id}">${message(code: 'default.button.edit.label', default: 'Edit')}</g:link>
                        </td>
                        <td class="action">
                            <g:form method="post" action="delete">
                                <g:hiddenField name="id" value="${interestRateInstance?.id}" />
                                <g:hiddenField name="version" value="${interestRateInstance?.version}" />
                                <a href="#delete">${message(code: 'default.button.delete.label', default: 'Delete')}</a>
                            </g:form>
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
            </div>
            <div class="pagination">
                <g:paginate total="${interestRateInstanceTotal}" />
            </div>
        </div>
        <script type="text/javascript">
            jQuery(function() {
                $('a[href="#delete"]').click(function(e) {
                    e.preventDefault();
                    if (confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}')) {
                        $(this).parent('form').submit();
                    }
                });
            });
        </script>
    </body>

</html>
