package th.co.opendream.cashcard.domain

class InterestRate {
    Date startDate
    Float rate
    Date dateCreated
    Date lastUpdated

    static constraints = {
        startDate unique: true, nullable: false, blank: false
        rate min: 0F, nullable: false, blank: false
    }
}
