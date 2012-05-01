<!doctype html>
<html>
    <head>
        <meta name="layout" content="main">
        <title>สรุปรายการรับ/จ่าย</title>
        <style type="text/css">
            td.right {
                text-align: right;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <header class="page-header">
                <h1>สรุปประจำวัน</h1>
            </header>
        </div>

        <div class="control-group">
            <form id="dailySummary" action="${createLink (action:'dailySummary')}">
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
                      <th></th>
                      <th></th>
                      <th></th>
                      <th></th>
                      <th colspan="3">รับชำระเงินกู้</th>
                    </tr>
                    <tr>
                      <th class="date">วัน/เวลา</th>
                      <th class="number">หมายเลขสมาชิก</th>
                      <th class="string">ชื่อสมาชิก</th>
                      <th class="number">จ่ายเงินกู้</th>
                      <th class="number">เงินต้น</th>
                      <th class="number">ดอกเบี้ย</th>
                      <th class="number">ส่วนต่าง</th>
                    </tr>
                </thead>
                <tbody>
                <g:each in="${results}" status="i" var="tx">
                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                        <td class="date"><g:formatDate format="dd/MM/yyyy HH:mm" date="${tx.date}" /></td>
                        <td class="number"><a href="${createLink(controller:'member', action:'show', params:[id: tx.memberID])}">${tx.memberIDCard}</a></td>
                        <td class="id">${tx.date != null ? tx.member : 'รวม'}</td>
                        <td class="number"><g:formatNumber type="number" number="${tx.withdraw}" maxFractionDigits="2" minFractionDigits="2" /></td>
                        <td class="number"><g:formatNumber type="number" number="${tx.pay}" maxFractionDigits="2" minFractionDigits="2" /></td>
                        <td class="number"><g:formatNumber type="number" number="${tx.interest}" maxFractionDigits="2" minFractionDigits="2" /></td>
                        <td class="number"><g:formatNumber type="number" number="${tx.remainder}" maxFractionDigits="2" minFractionDigits="2" /></td>
                    </tr>
                </g:each>
                </tbody>
            </table>
            </div>
        </div>
    </body>

</html>
