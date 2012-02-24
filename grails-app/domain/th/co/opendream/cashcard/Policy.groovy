package th.co.opendream.cashcard

class Policy {
	String key
	String value

    static String CREDIT_LINE = 'CreditLine'
    
    static constraints = {
        key(unique: true) 
    }

    static mapping = {
    	
    }
}
