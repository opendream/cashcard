package th.co.opendream.cashcard

class Company {
    String name
    String address
    String taxId
    Date dateCreated
    Date lastUpdated

    static hasMany = [users:Users, members:Member]

    static constraints = {
    	name(blank:false, unique:true)
    }
}