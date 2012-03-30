package th.co.opendream.cashcard

import org.springframework.transaction.support.TransactionSynchronizationManager
import grails.util.Environment

class ApiService {
    def sessionFactory

    def getMemberTransactionHistory(Member member) {
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
            WHERE member_id = " + member.id + " AND class = 'th.co.opendream.cashcard.BalanceTransaction' \
            ORDER BY date ASC\
        "

        sql.eachRow(query) {
            transactionHistory << it.toRowResult()
        }

        transactionHistory
    }
}
