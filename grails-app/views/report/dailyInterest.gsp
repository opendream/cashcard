<!doctype html>
<html>
    <head>
        <meta name="layout" content="main">
        <title><g:message code="report.dailyinterest.title"></g:message></title>
        <style type="text/css">
            td.right {
                text-align: right;
            }
            select#startDate_year {
                margin-right: 20px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <header class="page-header">
                <h1><g:message code="report.dailyinterest.title"></g:message></h1>
            </header>
        </div>

        <div class="control-group">
            <form id="dailyInterest" action="${createLink (action:'dailyInterest')}">
            <div class="container">
                <label for="startDate" class="control-label">
                    <g:message code="cashcard.date.from"></g:message>
                </label>
                <g:datePicker name="startDate" precision="day"  value="${startDate}"  />
                <label for="endDate" class="control-label">
                    <g:message code="cashcard.date.to"></g:message>
                </label>
                <g:datePicker name="endDate" precision="day"  value="${endDate}"  />
                <button class="btn btn-primary" type="submit"><i class="icon-ok icon-white"></i>ค้นหา</button>
            </div>
            </form>
        </div>

        <div id="list-interestRate" class="content scaffold-list" role="main">
            <g:if test="${flash.message}">
                <div class="message alert alert-success" role="status">${flash.message} <a class="close" data-dismiss="alert">×</a></div>
            </g:if>
            <div class="container">
            <table class="table table-striped table-bordered table-condensed">
                <thead>
                    <tr>
                        <th class="id">${message(code: 'report.dailyinterest.table.id', default: '#')}</th>
                        <th class="date">${message(code: 'report.dailyinterest.table.date', default: 'Date')}</th>
                        <th class="string">${message(code: 'report.dailyinterest.table.member', default: 'Member')}</th>
                        <th class="number">${message(code: 'report.dailyinterest.table.balance', default: 'Balance')}</th>
                        <th class="number">${message(code: 'report.dailyinterest.table.accumulatedInterest', default: 'Accumulated Interest')}</th>
                        <th class="number">${message(code: 'report.dailyinterest.table.fee', default: 'Fee')}</th>
                        <th class="number">${message(code: 'report.dailyinterest.table.interest', default: 'Interest')}</th>
                        <th class="number">${message(code: 'report.dailyinterest.table.totalInterest', default: 'Total Interest')}</th>
                    </tr>
                </thead>
                <tbody>
                <g:each in="${interestList}" status="i" var="interest">
                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                        <td class="id">${i+1}</td>
                        <td class="string"><g:formatDate format="dd/MM/yyyy HH:mm" date="${interest.date}" /></td>
                        <td class="string"><a href="${createLink(controller:'member', action:'show', params:[id: interest.member.id])}">${interest.member}</a></td>
                        <td class="number"><g:formatNumber type="number" number="${interest.balanceForward}" maxFractionDigits="2" minFractionDigits="2" /></td>
                        <td class="number"><g:formatNumber type="number" number="${interest.interestForward}" maxFractionDigits="2" minFractionDigits="2" /></td>
                        <td class="number"><g:formatNumber type="number" number="${interest.fee}" maxFractionDigits="2" minFractionDigits="2" /></td>
                        <td class="number"><g:formatNumber type="number" number="${interest.interest}" maxFractionDigits="2" minFractionDigits="2" /></td>
                        <td class="number"><g:formatNumber type="number" number="${interest.amount + interest.interestForward}" maxFractionDigits="2" minFractionDigits="2" /></td>

                    </tr>
                </g:each>
                </tbody>
            </table>
            </div>
            <div class="pagination">
                <g:paginate total="${interestList}" />
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
