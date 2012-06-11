<%@ page import="th.co.opendream.cashcard.InterestRate" %>
<!doctype html>
<html>
    <head>
        <meta name="layout" content="main">
        <title>${memberInstance}: <g:message code="member.transaction.title"></g:message></title>
        <style type="text/css">
            td.right {
                text-align: right;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <header class="page-header">
                <h1>${memberInstance}: <g:message code="member.transaction.title"></g:message></h1>
            </header>
        </div>

        <g:render template="toolbar" />

        <div id="list-transaction" class="content scaffold-list" role="main">
            <g:if test="${flash.message}">
                <div class="message alert alert-success" role="status">${flash.message} <a class="close" data-dismiss="alert">×</a></div>
            </g:if>
            <div class="container">
            <table class="table table-striped table-bordered table-condensed">
                <thead>
                    <tr>
                        <th class="date"> ${message(code: 'cashcard.date', default: 'Date')}</th>
                        <th class="string"> ${message(code: 'transaction.activityType', default: 'Activity Type')}</th>
                        <th class="number"> เดบิต</th>
                        <th class="number"> เครดิต</th>
                        <th class="nubmer"> ยอดคงเหลือ</th>
                        <th class="string">หมายเหตุ</th>
                    </tr>
                </thead>
                <tbody>
                <g:each in="${transactionList}" status="i" var="tx">
                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                        <td class="date"><g:formatDate format="dd/MM/yyyy HH:mm" date="${tx.date}" /></td>
                        <td class="string">${message(code: 'transaction.activityType.'+tx.activity, default: '')}</td>
                        <td class="number"><g:formatNumber type="number" number="${tx.debit}" maxFractionDigits="2" minFractionDigits="2" /></td>
                        <td class="number"><g:formatNumber type="number" number="${tx.credit}" maxFractionDigits="2" minFractionDigits="2" /></td>
                        <td class="number"><g:formatNumber type="number" number="${tx.balance}" maxFractionDigits="2" minFractionDigits="2" /></td>
                        <td class="string">${tx.remark}</td>
                    </tr>
                </g:each>
                </tbody>
            </table>
            </div>
            <div class="pagination">
                <g:paginate total="${transactionCount}" />
            </div>
        </div>
    </body>

</html>
