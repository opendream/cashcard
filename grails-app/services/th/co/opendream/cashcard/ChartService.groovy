package th.co.opendream.cashcard


import org.springframework.transaction.support.TransactionSynchronizationManager

class ChartService {

    def schemaService
    def sessionFactory

    def relate() {
        def index = [:]
        def rows = []
        def coms = Company.findAll([order: 'id'])
        coms.eachWithIndex {orig, y ->
            def cols = []
            rows << cols
            coms.eachWithIndex {dest, x ->
                def tmp = [amount: 0.00]
                cols << tmp
                def key = "${orig.id}:${dest.id}"
                println key
                index[key] = tmp
            }
        }
        println '---------'

        def holder = TransactionSynchronizationManager.getResource(sessionFactory)
        def session = holder.getSession();
        def conn = session.connection();
        groovy.sql.Sql sql = new groovy.sql.Sql(conn)

        coms.each {
            def schema = "c${it.id}"
            sql.eachRow("select user_company_id, member_company_id, amount from "+ schema + ".transaction where code != 'INT' and transfer_type != 'RECEIVE'") {
                println it
                def orig = it.user_company_id
                def dest = it.member_company_id
                def key = "${orig}:${dest}"
                println key
                index[key].amount += it.amount
            }
        }

        rows
    }
}