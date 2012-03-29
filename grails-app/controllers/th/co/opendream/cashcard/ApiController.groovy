package th.co.opendream.cashcard

import grails.converters.JSON
import org.springframework.transaction.support.TransactionSynchronizationManager
import grails.util.Environment

class ApiController {
	def schemaService
    def sessionFactory

    def index() { }

    def getTransactionHistory() {
    	def c

		schemaService.with(params.companySchema) {
	    	c = BalanceTransaction.createCriteria()
            c = c.setCacheable(false).list()

			render c as JSON
        }
    }
}