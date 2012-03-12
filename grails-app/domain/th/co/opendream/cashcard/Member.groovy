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

    Company company
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
        company(nullable:true)
    }

    static mapping = {

    }

    BigDecimal getBalance() {
        this.balance
    }

    /**
     * วงเงินที่ยังยืมได้
     */
    BigDecimal getRemainingFinancialAmount() {
        def limit = Policy.valueOfCreditLine()
        return limit - getTotalDebt()
    }

    void withdraw(BigDecimal amount) {
        transactionService.withdraw(this, amount)
    }

    Boolean canWithdraw(amount) {
        return amount <= getRemainingFinancialAmount() && amount > 0.00
    }

    /**
     * หนี้คงค้างสุทธิ
     */
    BigDecimal getTotalDebt() {
        if (Policy.isCompoundMethod()) {
            balance
        }
        else {
            balance + interest
        }
    }
    // amount = ยอดเงินที่ชำระจริง
    def pay(BigDecimal amount) {
        transactionService.pay(this, amount)
    }
}
