package th.co.opendream.cashcard

class Company {
    Long id
    String name
    String address
    String taxId

    String getSchema() {
        "c$id"
    }

    static transients = ['schema']

    static hasMany = [users:Users, members:Member]

}