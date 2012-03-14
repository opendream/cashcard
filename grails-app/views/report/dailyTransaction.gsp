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

        <div class="control-group">
            <form action="${createLink (action:'dailyTransaction')}">
            <div class="container">
                <label for="startDate" class="control-label">
                ระหว่างวันที่
                </label>
                <g:datePicker name="startDate" precision="day"  value="${startDate}"  />
                <label for="endDate" class="control-label">
                ถึงวันที่
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
                       <th class="string">${message(code: 'report.dailyinterest.table.date', default: 'Date')}</th>
                       <th class="string">${message(code: 'report.dailyinterest.table.member', default: 'Member')}</th>
                       <th class="string">${message(code: 'report.dailyinterest.table.code', default: 'Code')}</th>
                       <th class="number">${message(code: 'report.dailyinterest.table.amount', default: 'Amount')}</th>
                       <th class="number">${message(code: 'report.dailyinterest.table.remainder', default: 'ส่วนต่างปัดเศษ')}</th>
                    </tr>
                </thead>
                <tbody>
                <g:each in="${results}" status="i" var="tx">
                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                        <td class="id">${i+1}</td>
                        <td class="date"><g:formatDate format="yyyy-MM-dd HH:mm" date="${tx.date}" /></td>
                        <td class="string">${tx.member}</td>
                        <td class="string">${tx.code}</td>
                        <td class="number"><g:formatNumber type="number" number="${tx.amount}" maxFractionDigits="2" minFractionDigits="2" /></td>
                        <td class="number"><g:formatNumber type="number" number="${tx.remainder}" maxFractionDigits="2" minFractionDigits="2" /></td>
                    </tr>
                </g:each>
                </tbody>
            </table>
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
