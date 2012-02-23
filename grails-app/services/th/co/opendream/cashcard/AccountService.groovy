package th.co.opendream.cashcard

import th.co.opendream.cashcard.Member
import th.co.opendream.cashcard.PolicyService

class AccountService {
    static transactional = true
    def policyService

    BigDecimal getBalance(Member member) {
        member.balance
    }

    void withdraw(Member member, amount) {
        amount = amount as BigDecimal
        if (amount <= 0) {
           throw new RuntimeException(message: "Withdraw amount is less than or equal 0 : ${amount}")
        }
        member.balance += amount
        member.save()
    }

    def canWithdraw(Member member, amount) {
        def limit = policyService.getGlobalFinancialAmountLimit()
        amount = amount as BigDecimal

        if (limit < amount + member.balance) {
            return false
        }
        else if (amount <= 0) {
            return false
        }
        else if (limit >= amount + member.balance) {
            return true
        }
        else {
            return false
        }
    }

    def getBalanceList() {
        Member.findAllByBalanceGreaterThan(0.00).collect {
            [ accountId: it.id, balance: it.balance ]
        }
    }

}
