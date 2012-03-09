package th.co.opendream.cashcard

class Member {
    String identificationNumber
    String firstname
    String lastname
    Gender gender
    String address
    String telNo
    BigDecimal balance = 0.00
    BigDecimal interest = 0.00
    Date dateCreated
    Date lastUpdated

    static hasMany = [balanceTransactions: BalanceTransaction]

    def transactionService
    def utilService

    public enum Gender {
      MALE,
      FEMALE
      static list() {
       [MALE, FEMALE]
      }
    }

    String toString() {
        "${firstname} ${lastname}"
    }

    static constraints = {
        identificationNumber(blank: false, unique: true, matches: /\d{13}/)
        firstname(blank: false)
        lastname(blank: false)
        address(blank: false)
        telNo(nullable:true, matches: /\d{9,11}/)
        balance(blank: true, nullable: true)
        interest(blank: true, nullable: true)
    }

    static mapping = {

    }

    BigDecimal getBalance() {
        this.balance
    }

    BigDecimal getRemainingFinancialAmount() {
        def balance = this.balance
        def limit = Policy.valueOfCreditLine()

        if (Policy.isCompoundMethod()) {
            return limit - balance
        } else {
            return limit - balance - this.interest
        }
    }

    void withdraw(BigDecimal amount) {
        transactionService.withdraw(this, amount)
    }

    Boolean canWithdraw(amount) {
        def limit = Policy.valueOfCreditLine()
        amount = amount as BigDecimal
        if (limit < amount + this.balance) {
            return false
        }
        else if (amount <= 0) {
            return false
        }
        else if (limit >= amount + this.balance) {
            return true
        }
        else {
            return false
        }
    }

    BigDecimal getInterest() {
        this.interest
    }

    BigDecimal getTotalDebt() {
        if (Policy.isCompoundMethod()) {
            balance
        }
        else {
            balance + interest
        }
    }
    // amount = ยอดเงินที่ชำระจริง
    BigDecimal pay(BigDecimal amount) {
        transactionService.pay(this, amount)
    }
}
