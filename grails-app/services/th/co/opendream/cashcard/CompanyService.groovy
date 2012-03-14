package th.co.opendream.cashcard

class CompanyService {
	def schemaService

    def save(company) {
    	company.save(failOnError: true)
    	schemaService.create(company.schema)
    	schemaService.with(company.schema) {
        	new Policy(key: Policy.KEY_CREDIT_LINE, value: 2000).save()
        	new Policy(key: Policy.KEY_INTEREST_METHOD, value: Policy.VALUE_NON_COMPOUND).save()
        	new Policy(key: Policy.KEY_INTEREST_RATE_LIMIT, value: '18.00').save()
        	
        	new InterestRate(startDate:new Date(), rate:9.00).save()           
        } 
    }
}
