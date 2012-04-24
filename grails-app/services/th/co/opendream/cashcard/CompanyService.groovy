package th.co.opendream.cashcard

class CompanyService {
	def schemaService

    def save(company) {
    	company.save(failOnError: true)
    	schemaService.create(company.schema)
    	schemaService.with(company.schema) {
        	new InterestRate(startDate:new Date(), rate:9.00).save()           
        } 
    }
}
