package th.co.opendream.cashcard

class Member {
    String identificationNumber
    String firstname
    String lastname
    Gender gender
    Status status = Status.ACTIVE
    String address
    String telNo
    BigDecimal balance = 0.000000
    BigDecimal interest = 0.000000
    Date dateCreated
    Date lastUpdated

    Company company
    static hasMany = [balanceTransactions: BalanceTransaction]

    def transactionService
    def utilService
    def policyService

    public enum InterestMethod {
        COMPOUND,
        NON_COMPOUND,
        static list() {
            [COMPOUND, NON_COMPOUND]
        }
    }

    public enum Gender {
      MALE,
      FEMALE,
      static list() {
       [MALE, FEMALE]
      }
    }

    public enum Status {
        ACTIVE,
        DELETED
        static list() {
            [ACTIVE, DELETED]
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
        telNo(blank: false, matches: /\d{9,11}/)
        balance(blank: true, nullable: true)
        interest(blank: true, nullable: true)
        company(nullable:true)
    }

    static mapping = {
        balance sqlType: "numeric(19,6)"
        interest sqlType: "numeric(19,6)"
    }

    BigDecimal getBalance() {
        balance
    }

    BigDecimal getRealBalance() {
        if (policyService.getInterestMethod(this.company) == Policy.VALUE_COMPOUND) {
            balance - interest
        }
        else {
            balance
        }
    }

    /**
     * วงเงินที่ยังยืมได้
     */
    BigDecimal getRemainingFinancialAmount() {
        def limit = policyService.getCreditLine(this.company)
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
        if (policyService.getInterestMethod(this.company) == Policy.VALUE_COMPOUND) {
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
