package th.co.opendream.cashcard


class AccountService {
    static transactional = true

    def getBalanceList(company) {
        Member.findAllByCompanyAndBalanceGreaterThan(company, 0.00).collect {
            [ accountId: it.id, balance: it.balance ]
        }
    }

}