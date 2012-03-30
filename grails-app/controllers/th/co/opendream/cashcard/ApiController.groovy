package th.co.opendream.cashcard

import grails.converters.JSON
import org.springframework.transaction.support.TransactionSynchronizationManager
import grails.util.Environment

class ApiController {
    def sessionFactory
	def schemaService

    def index() { }

    def getMemberTransactionHistory() {
        def member = Member.get(params.memberId as Integer)

        def holder = TransactionSynchronizationManager.getResource(sessionFactory)
        def session = holder.getSession();
        def conn = session.connection();
        groovy.sql.Sql sql = new groovy.sql.Sql(conn)

        // TODO: Prevent SQL injection in GString. (include null or empty string)
        def transactionHistory = []
        def schema = member.company.getSchema()
        def query = "\
            SELECT * \
            FROM " + schema + ".transaction\
            WHERE member_id = " + member.id + "\
            ORDER BY date ASC\
        "

        sql.eachRow(query) {
            transactionHistory << it.toRowResult()
        }
        render transactionHistory as JSON
    }
}