package th.co.opendream.cashcard.domain

class InterestRate {
    Date startDate
    Date endDate
    Float rate
    Date dateCreated
    Date lastUpdated

    static constraints = {
        startDate unique: true, nullable: false, blank: false
        endDate unique: true, nullable: false, blank: false, validator: { val, obj -> val >= obj.startDate?.plus(1) }
        rate min: 0F, nullable: false, blank: false
    }
}
