package th.co.opendream.cashcard


class AccountService {
    static transactional = true

    def getBalanceList() {
        Member.findAllByBalanceGreaterThan(0.00).collect {
            [ accountId: it.id, balance: it.balance, effectiveBalance: it.getTotalDebt() ]
        }
    }

}