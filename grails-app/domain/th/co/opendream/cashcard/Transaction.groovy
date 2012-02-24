package th.co.opendream.cashcard

abstract class Transaction {
	BigDecimal amount
    TransactionType txType 
    Member member
    String code
    Date date
    
    static constraints = {
        amount validator: { val, obj ->
            val > 0.00 ? null : "notValidAmount"
        }
    }

}


public enum TransactionType {
    CREDIT,
    DEBIT
}

