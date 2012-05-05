package th.co.opendream.cashcard

class InterestTransaction extends Transaction {
    BigDecimal fee
    BigDecimal interest
    BigDecimal interestForward
    BigDecimal balanceForward

    static constraints = {
        fee validator: { val, obj ->
            (val == obj.amount - obj.interest) ? null : "BalanceNotEqual"
        }
    }

    def beforeValidate() {
        if (! code) {
            code = "INT"
        }
    }
}
