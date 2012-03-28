package th.co.opendream.cashcard

import grails.converters.JSON

class ApiController {
	def schemaService


    def index() { }

    def getTransactionHistory() {
    	println "F"
    	println params
    	def c
		schemaService.with(params.companySchema) {
	    	c = BalanceTransaction.list()
			render c as JSON
			return
    	}
    }
}