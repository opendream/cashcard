package th.co.opendream.cashcard

class BalanceTransaction extends Transaction {
    ActivityType activity
    BigDecimal net
    BigDecimal remainder

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
        remainder validator: { val, obj ->
            (val == obj.amount - obj.net) ? null : "RemainderAmountNotEqual"
        }
    }
    
}

public enum ActivityType {
    PAYMENT,
    WITHDRAW
}