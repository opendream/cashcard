package th.co.opendream.cashcard.service

import th.co.opendream.cashcard.domain.Member

class AccountService {
    static transactional = true

    Float getBalance(Member member) {
        return member.balance
    }

    void withdraw(Member member, amount) {
        amount = new Float(amount)
        if (amount <= 0) {
           throw new RuntimeException(message: "Withdraw amount is less than or equal 0 : ${amount}")
        }
        member.balance -= amount
        member.save()
    }

    def canWithdraw(Member member, amount) {
        amount = new Float(amount)
        if (amount > member.balance) {
            return false
        }
        else if (amount <= 0) {
            return false
        }
        else if (amount <= member.balance) {
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
