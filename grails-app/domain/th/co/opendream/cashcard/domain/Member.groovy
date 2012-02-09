package th.co.opendream.cashcard.domain

class Member {
	String identificationNumber
	String firstname
	String lastname
	Gender gender
	Date dateCreated
  Date lastUpdated

   	public enum Gender {
   		MALE,
   		FEMALE
   		static list() {
			 [MALE, FEMALE]
		  }
   	}

    static constraints = {
    	identificationNumber(blank: false, unique: true, matches: /\d{13}/)
    	firstname(blank: false)
    	lastname(blank: false)
    }

    static mapping = {
    	
    }
}
