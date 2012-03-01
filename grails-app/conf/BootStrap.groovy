import th.co.opendream.cashcard.Member
import th.co.opendream.cashcard.Policy

class BootStrap {

    def init = { servletContext ->
    	def d1 = new Member(identificationNumber:"1159900100015", firstname:"Nat", lastname: "Weerawan", telNo: "111111111", gender: "MALE", address: "Opendream")
    	def d2 = new Member(identificationNumber: "1234567891234", firstname: "Noomz", lastname: "Siriwat", telNo: "111111111", gender: "MALE", address: "Opendream2")

    	d1.save()
    	d2.save()

    	new Policy(key: Policy.KEY_CREDIT_LINE, value: 2000).save()
    	new Policy(key: Policy.KEY_INTEREST_METHOD, value: Policy.VALUE_NON_COMPOUND).save()
    	new Policy(key: Policy.KEY_INTEREST_RATE_LIMIT, value: '18.00').save()

    }
    def destroy = {
    }
}
