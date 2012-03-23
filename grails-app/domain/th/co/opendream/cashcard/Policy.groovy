package th.co.opendream.cashcard

class Policy {
	String key
	String value

    static String KEY_CREDIT_LINE = 'CreditLine'
    static String KEY_INTEREST_RATE_LIMIT = 'InterestRateLimit'

    static constraints = {
        key(unique: true)
    }

    static String valueOf(key) {
        Policy.findByKey(key)?.value
    }

    static BigDecimal valueOfCreditLine() {
        new BigDecimal(valueOf(KEY_CREDIT_LINE))
    }

    static BigDecimal valueOfInterestRateLimit() {
        new BigDecimal(valueOf(KEY_INTEREST_RATE_LIMIT))
    }
}
