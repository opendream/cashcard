package th.co.opendream.cashcard

class Company {
    String name
    String address
    String taxId

    static hasMany = [users:Users, members:Member]
    
}