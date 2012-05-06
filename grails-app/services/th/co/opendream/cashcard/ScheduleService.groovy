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

                // return map
                // key -> accountId
                // value ->
                def existInterest = interestService.getInterestTransaction(now)

                accounts.each { ac ->
                    if (!accounts[ac.accountId]) {
                        def tx = interestService.calculate(now, ac.balance, rate, rateLimit)
                        tx.balanceForward = ac.member.balance
                        tx.interestForward = ac.member.interest

                        def amount = tx.interest + tx.fee

                        // update member's accumulated interest and compound new balance
                        def member = interestService.update(ac.member, amount)

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

}