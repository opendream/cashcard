<!doctype html>
<html>
    <head>
        <meta name="layout" content="main">
        <title>Report: Daily Interest</title>
        <style type="text/css">
            td.right {
                text-align: right;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <header class="page-header">
                <h1>Report: Daily Interest</h1>
            </header>
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
                        <td class="id">${interest.id}</td>
                        <td class="string">${interest.member}</td>
                        <td class="number"><g:formatNumber type="number" number="${interest.member.balance}" maxFractionDigits="2" minFractionDigits="2" /></td>
                        <td class="number"><g:formatNumber type="number" number="${interest.member.interest}" maxFractionDigits="2" minFractionDigits="2" /></td>
                        <td class="number"><g:formatNumber type="number" number="${interest.fee}" maxFractionDigits="2" minFractionDigits="2" /></td>
                        <td class="number"><g:formatNumber type="number" number="${interest.interest}" maxFractionDigits="2" minFractionDigits="2" /></td>
                        <td class="number"><g:formatNumber type="number" number="${interest.amount}" maxFractionDigits="2" minFractionDigits="2" /></td>
                     
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
