<!doctype html>
<html>
    <head>
        <meta name="layout" content="main">
        <title>สรุปเงินกู้คงค้าง</title>
        <style type="text/css">
            td.right {
                text-align: right;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <header class="page-header">
                <h1>สรุปเงินกู้คงค้าง</h1>
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
                       <th class="id">#</th>
                       <th class="string">${message(code: 'report.dailyinterest.table.member', default: 'Member')}</th>
                       <th class="number">เงินกู้</th>
                       <th class="number">ดอกเบี้ย</th>
                    </tr>
                </thead>
                <tbody>
                <g:each in="${results}" var="tx" status="i">
                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                        <td class="id">${i+1}</td>
                        <td class="string">${tx.name}</td>
                        <td class="number"><g:formatNumber type="number" number="${tx.balance}" maxFractionDigits="2" minFractionDigits="2" /></td>
                        <td class="number"><g:formatNumber type="number" number="${tx.interest}" maxFractionDigits="2" minFractionDigits="2" /></td>
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
