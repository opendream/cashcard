<!doctype html>
<html>
    <head>
        <meta name="layout" content="main">
        <title>รายการ Net Settlement</title>
        <style type="text/css">
            td.right {
                text-align: right;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <header class="page-header">
                <h1>รายการ Net Settlement</h1>
            </header>
        </div>

        <div class="control-group">
            <form id="dailyInterest" action="${createLink (action:'settlement')}">
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
                       <th class="number">เรียกเก็บ</th>
                       <th class="number">จ่ายคืน</th>
                    </tr>
                </thead>
                <tbody>
                <g:each in="${results}" status="i" var="tx">
                    <tr>
                        <td>${tx.name}</td>
                        <td class="number"><g:formatNumber type="number" number="${tx.receive}" maxFractionDigits="2" minFractionDigits="2" /></td>
                        <td class="number"><g:formatNumber type="number" number="${tx.sent}" maxFractionDigits="2" minFractionDigits="2" /></td>
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
