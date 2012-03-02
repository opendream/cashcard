<%@ page import="th.co.opendream.cashcard.InterestRate" %>
<!doctype html>
<html>
    <head>
        <meta name="layout" content="main">
        <title>${memberInstance}: Transaction</title>
        <style type="text/css">
            td.right {
                text-align: right;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <header class="page-header">
                <h1>${memberInstance}: Transaction</h1>
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
                        <th class="id">${message(code: 'member.transaction.transactionId', default: 'Transaction ID')}</th>
                        <th class="date"> ${message(code: 'date', default: 'Date')}</th>
                        <th class="string"> ${message(code: 'member.transaction.activityType', default: 'Activity Type')}</th>
                        <th class="number"> ${message(code: 'member.transaction.amount', default: 'Amount')}</th>
                        <th class="number"> ${message(code: 'member.transaction.net', default: 'Net')}</th>
                        <th class="number"> ${message(code: 'member.transaction.remainder', default: 'Remainder')}</th>
                    </tr>
                </thead>
                <tbody>
                <g:each in="${transactionList}" status="i" var="tx">
                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                        <td class="id">${tx.id}</td>
                        <td class="date"><g:formatDate format="yyyy-MM-dd" date="${tx.date}" /></td>
                        <td class="string">${message(code: 'member.transaction.'+tx.activity, default: '')}</td>
                        <td class="number"><g:formatNumber type="number" number="${tx.amount}" maxFractionDigits="2" minFractionDigits="2" /></td>
                        <td class="number"><g:formatNumber type="number" number="${tx.net}" maxFractionDigits="2" minFractionDigits="2" /></td>
                         <td class="number"><g:formatNumber type="number" number="${tx.remainder}" maxFractionDigits="2" minFractionDigits="2" /></td>
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
