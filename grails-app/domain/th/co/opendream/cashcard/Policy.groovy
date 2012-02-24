package th.co.opendream.cashcard

class Policy {
	String key
	String value

    static String KEY_CREDIT_LINE = 'CreditLine'
    static String KEY_INTEREST_METHOD = 'InterestMethod'
    
    static String VALUE_COMPOUND = 'CompoundInterest'
    static String VALUE_NON_COMPOUND = 'NonCompoundInterest'
    
    static constraints = {
        key(unique: true) 
    }

    static String valueOf(key) {
        Policy.findByKey(key)?.value
    }
}
