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

    void withdraw(amount) {
        amount = amount as BigDecimal
        if (amount <= 0) {
           throw new RuntimeException(message: "Withdraw amount is less than or equal 0 : ${amount}")
        }

        if (this.canWithdraw(amount)) {
            this.balance += amount
            if (transactionService.withdraw(this, amount)) {
                this.save()
            }
        }
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

    BigDecimal pay(net) {
        net = net as BigDecimal
        def amount = utilService.moneyRoundUp(net)

        def change = 0.00
        if (net >= interest) {
            if (Policy.isCompoundMethod()) {
                balance -= net
                interest = 0.00
            }
            else {
                balance -= net - interest
                interest = 0.00
            }
        }
        else if (net < interest) {
            if (Policy.isCompoundMethod()) {
                balance -= net
                interest -= net
            }
            else {
                interest -= net
            }
        }

        if (balance < 0.00) {
            change = -balance
            balance = 0.00
        }

        if (transactionService.pay(this, amount, net)) {
            this.save()
        }

        return change
    }
}
