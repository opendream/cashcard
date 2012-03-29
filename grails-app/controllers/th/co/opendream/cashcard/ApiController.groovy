package th.co.opendream.cashcard

import grails.converters.JSON
import org.springframework.transaction.support.TransactionSynchronizationManager
import grails.util.Environment

class ApiController {
	def schemaService
    def sessionFactory

    def index() { }

    def getTransactionHistory() {
    	println "F"
    	println params
    	def c

		//schemaService.with(params.companySchema) {
	    //	c = BalanceTransaction.list()
		//	render c as JSON
		//	return
        def holder = TransactionSynchronizationManager.getResource(sessionFactory)
        def session = holder.getSession();
        def conn = session.connection();
        groovy.sql.Sql sql = new groovy.sql.Sql(conn)

        // TODO: Prevent SQL injection in GString. (include null or empty string)
        def transactionHistory = []
        def query = "SELECT * FROM " + params.companySchema + ".transaction"
        println query
        sql.eachRow(query) {
            transactionHistory << it.toRowResult()
        }
        render transactionHistory as JSON
    }
}