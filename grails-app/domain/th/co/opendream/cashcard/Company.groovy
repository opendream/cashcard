package th.co.opendream.cashcard

class Company {
    Long id
    String name
    String address
    String taxId
    Date dateCreated
    Date lastUpdated

    String getSchema() {
        "c$id"
    }

    static transients = ['schema']

    static hasMany = [users:Users, members:Member]

    static constraints = {
    	name(blank:false, unique:true)
    }
}