package th.co.opendream.cashcard

class BalanceTransaction extends Transaction {
    ActivityType activity
    BigDecimal net
    BigDecimal remainder
    BigDecimal balance
    BigDecimal balance_pay
    BigDecimal interest_pay
    TransferType transferType = TransferType.NONE
    Company userCompany
    Company memberCompany

    def beforeValidate() {
        if (! code) {
            if (activity == ActivityType.PAYMENT) {
                code = "PAY"
            }
            else if (activity == ActivityType.WITHDRAW) {
                code = "WDR"
            }
        }
    }

    static constraints = {
        userCompany nullable: true
        memberCompany nullable: true
        transferType nullable: true
        remainder validator: { val, obj ->
            (val == obj.amount - obj.net) ? null : "RemainderAmountNotEqual"
        }
    }

}

public enum ActivityType {
    PAYMENT,
    WITHDRAW
}

public enum TransferType {
    SENT,
    RECEIVE,
    NONE
}