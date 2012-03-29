package th.co.opendream.cashcard

import grails.converters.JSON
import org.springframework.transaction.support.TransactionSynchronizationManager
import grails.util.Environment

class ApiController {
	def schemaService
    def sessionFactory

    def index() { }

    def getTransactionHistory() {
        def member = Member.get(params.memberId as Integer)
    	def c

		schemaService.with(member.company.getSchema()) {
	    	c = member.balanceTransactions.sort { it.date }
			render c as JSON
        }
    }
}