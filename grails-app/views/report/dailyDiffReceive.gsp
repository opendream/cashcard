<!doctype html>
<html>
    <head>
        <meta name="layout" content="main">
        <title>สรุปรายการรับ/จ่าย จากสหกรณ์อื่น</title>
        <style type="text/css">
            td.right {
                text-align: right;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <header class="page-header">
                <h1>สรุปรายการรับ/จ่าย จากสหกรณ์อื่น</h1>
            </header>
        </div>

        <div class="control-group">
            <form id="dailyInterest" action="${createLink (action:'dailyDiffReceive')}">
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
                       <th class="date">สหกรณ์</th>
                       <th class="id">${message(code: 'report.dailyinterest.table.id', default: '#')}</th>
                       <th class="date">${message(code: 'report.dailyinterest.table.date', default: 'Date')}</th>
                       <th class="string">${message(code: 'report.dailyinterest.table.member', default: 'Member')}</th>
                       <th class="date">${message(code: 'report.dailyinterest.table.code', default: 'Code')}</th>
                       <th class="number">Debit</th>
                       <th class="number">Credit</th>
                    </tr>
                </thead>
                <tbody>
                <g:each in="${results}" var="item">
                <tr>
                    <td>${item.key}</td>
                    <td colspan='6'></td>
                </tr>
                    <g:each in="${item.value}" var="tx" status="i">
                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                        <td class="date"></td>
                        <td class="id">${tx.code != 'รวมเงิน' ? i+1 : ''}</td>
                        <td class="date"><g:formatDate format="dd/MM/yyyy HH:mm" date="${tx.date}" /></td>
                        <td class="string">${tx.member}</td>
                        <td class="date">${tx.code}</td>
                        <td class="number"><g:formatNumber type="number" number="${tx.debit}" maxFractionDigits="2" minFractionDigits="2" /></td>
                        <td class="number"><g:formatNumber type="number" number="${tx.credit}" maxFractionDigits="2" minFractionDigits="2" /></td>
                    </tr>

                    </g:each>
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
