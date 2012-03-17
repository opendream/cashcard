package th.co.opendream.cashcard


class ScheduleService {
    static transactional = true
    def interestService
    def accountService
    def schemaService

    def updateInterest() {
        def now = new Date()
        Company.findAll().each { company ->
            def schema = 'c' + company.id
            schemaService.with(schema) {

                def rate = interestService.getRate(now)
                def rateLimit = Policy.valueOfInterestRateLimit()

                def accounts = accountService.getBalanceList(company)

                accounts.each { ac ->
                    println ac
                    def tx = interestService.calculate(now, ac.balance, rate, rateLimit)
                    def amount = tx.interest + tx.fee

                    // update member's accumulated interest and compound new balance
                    def member = interestService.update(ac.accountId, amount)

                    tx.amount = amount
                    tx.txType = TransactionType.CREDIT
                    tx.member = member
                    tx.date = new Date()

                    tx.save()
                }
            }
        }
    }

}