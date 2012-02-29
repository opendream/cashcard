import th.co.opendream.cashcard.Member
import th.co.opendream.cashcard.Policy
import th.co.opendream.cashcard.InterestTransaction
import th.co.opendream.cashcard.Transaction
import th.co.opendream.cashcard.TransactionType
import groovy.time.*
import static java.util.Calendar.*



class BootStrap {

    def init = { servletContext ->
    	def m1 = new Member(identificationNumber:"1159900100015", firstname:"Nat", lastname: "Weerawan", telNo: "111111111", gender: "MALE", address: "Opendream")
    	def m2 = new Member(identificationNumber: "1234567891234", firstname: "Noomz", lastname: "Siriwat", telNo: "111111111", gender: "MALE", address: "Opendream2")

    	m1.save()
    	m2.save()

    	new Policy(key: Policy.KEY_CREDIT_LINE, value: 2000).save()
    	new Policy(key: Policy.KEY_INTEREST_METHOD, value: Policy.VALUE_NON_COMPOUND).save()

        def today = Calendar.instance
        today = today.time

        new InterestTransaction([id: 1, member: m1, amount: 10.00, txType: TransactionType.CREDIT, date: today, fee: 0.00, interest: 10.00]).save()
        new InterestTransaction([id: 2, member: m2, amount: 21.00, txType: TransactionType.CREDIT, date: today, fee: 0.00, interest: 21.00]).save()
        new InterestTransaction([id: 3, member: m1, amount: 13.00, txType: TransactionType.CREDIT, date: today.plus(1), fee: 0.00, interest: 13.00]).save()
        new InterestTransaction([id: 4, member: m2, amount: 24.00, txType: TransactionType.CREDIT, date: today.plus(1), fee: 0.00, interest: 24.00]).save()
    }
    def destroy = {
    }
}
