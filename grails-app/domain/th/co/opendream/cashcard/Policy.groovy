package th.co.opendream.cashcard

class Policy {
    String key
    String value

    static belongsTo = [company: Company]

    static String KEY_CREDIT_LINE = 'CreditLine'
    static String KEY_INTEREST_METHOD = 'InterestMethod'
    static String KEY_INTEREST_RATE_LIMIT = 'InterestRateLimit'

    static String VALUE_COMPOUND = 'CompoundInterest'
    static String VALUE_NON_COMPOUND = 'NonCompoundInterest'

    static String valueOf(key) {
        Policy.findByKey(key)?.value
    }

    static BigDecimal valueOfCreditLine() {
        new BigDecimal(valueOf(KEY_CREDIT_LINE))
    }

    static boolean isCompoundMethod() {
        valueOf(KEY_INTEREST_METHOD) == VALUE_COMPOUND
    }

    static BigDecimal valueOfInterestRateLimit() {
        new BigDecimal(valueOf(KEY_INTEREST_RATE_LIMIT))
    }
}
