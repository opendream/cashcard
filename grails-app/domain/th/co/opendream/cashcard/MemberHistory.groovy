package th.co.opendream.cashcard

class MemberHistory {
    String identificationNumber
    String firstname
    String lastname
    Member.Gender gender
    Member.Status status
    String address
    String telNo
    BigDecimal balance = 0.00
    BigDecimal interest = 0.00
    Date dateCreated
    Date lastUpdated

	static belongsTo = [member: Member]

    static constraints = {
        identificationNumber(blank: false, unique: false, matches: /\d{13}/)
    }
}
