<%@ page import="th.co.opendream.cashcard.Member" %>

<html>
    <head>
        <meta name="layout" content="main">
        <title>${"${memberInstance.firstname} ${memberInstance.lastname}"}</title>
    </head>
    <body>
        <div class="container">
            <header class="page-header">
                <h1>${"${memberInstance.firstname} ${memberInstance.lastname}"}</h1>
            </header>
        </div>

        <div class="container">
                <g:if test="${flash.message}">
                    <div class="message alert alert-success" role="status">${flash.message}</div>
                </g:if>
                <g:render template="toolbar" />

                <h3>ข้อมูลสมาชิก</h3>
                <table class="table table-striped table-bordered">
                    <tr>
                        <td><strong><g:message code="member.label.identificationNumber"></g:message></strong></div>
                        <td>
                            ${memberInstance?.identificationNumber}
                        </td>
                    </tr>

                    <tr>
                        <td><strong><g:message code="member.label.firstName"></g:message></strong></div>
                        <td>
                            ${memberInstance?.firstname}
                        </td>
                    </tr>

                    <tr>
                        <td><strong><g:message code="member.label.lastName"></g:message></strong></td>
                        <td>
                            ${memberInstance?.lastname}
                        </td>
                    </tr>

                    <tr>
                        <td><strong><g:message code="member.label.telNo"></g:message></strong></td>
                        <td>
                            <g:if test="${memberInstance.telNo}">
                                ${memberInstance.telNo}
                            </g:if>
                            <g:else>-</g:else>
                        </td>
                    </tr>

                    <tr>
                        <td><strong><g:message code="member.label.gender"></g:message></strong></td>
                        <td>
                            ${message(code: 'member.label.'+memberInstance?.gender.toString().toLowerCase(), default: memberInstance?.gender.toString())}
                        </td>
                    </tr>

                    <tr>
                        <td><strong><g:message code="member.label.address"></g:message></strong></td>
                        <td>
                            <g:if test="${memberInstance.address}">
                                ${memberInstance.address}
                            </g:if>
                            <g:else>-</g:else>
                        </td>
                    </tr>
                </table>

                <h3>สถานะการเงิน</h3>
                <table class="table table-striped table-bordered">
                    <tr>
                        <td><strong><g:message code="member.label.creditLine"></g:message></strong></td>
                        <td>
                            <g:formatNumber number="${memberInstance?.getRemainingFinancialAmount()}" type="currency" currencyCode="THB" />
                        </td>
                    </tr>

                    <tr>
                        <td><strong><g:message code="member.label.balance"></g:message></strong></td>
                        <td>
                            <g:formatNumber number="${memberInstance?.balance}" type="currency" currencyCode="THB" />
                        </td>
                    </tr>

                    <tr>
                        <td><strong><g:message code="member.label.interest"></g:message></strong></td>
                        <td>
                            <g:formatNumber number="${memberInstance?.getInterest()}" type="currency" currencyCode="THB" />
                        </td>
                    </tr>

                    <tr>
                        <td><strong><g:message code="member.label.totalDebt"></g:message></strong></td>
                        <td>
                            <g:formatNumber number="${memberInstance?.getTotalDebt()}" type="currency" currencyCode="THB" />
                        </td>
                    </tr>
                </table>
                <div class="form-actions">
                    <g:link class="btn" action="edit" id="${memberInstance.id}">แก้ไขข้อมูลสมาชิก</g:link>
                </div>

            </div>

            <script type="text/javascript">
                jQuery(function() {
                    $('a[href="#disable"]').click(function(e) {
                        e.preventDefault();
                        if (confirm('${message(code: 'default.button.confirm.message', default: 'Are you sure?')}')) {
                            $(this).parent('form').submit();
                        }
                    });

                    $('a[href="#enable"]').click(function(e) {
                        e.preventDefault();
                        if (confirm('${message(code: 'default.button.confirm.message', default: 'Are you sure?')}')) {
                            $(this).parent('form').submit();
                        }
                    });
                });
            </script>

        </body>
</html>
