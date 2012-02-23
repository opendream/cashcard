package th.co.opendream.cashcard

class InterestRate {
    Date startDate
    BigDecimal rate
    Date dateCreated
    Date lastUpdated

    static constraints = {
        startDate unique: true, nullable: false, blank: false
        rate min: 0.00, nullable: false, blank: false
    }
}
