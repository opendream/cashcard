package th.co.opendream.cashcard

import th.co.opendream.cashcard.Member.Gender
import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
class MemberTests {

	@Before
	void setUp() {
        def m1 = new Member(identificationNumber:"1159900100015", firstname:"Nat", lastname: "Weerawan", telNo: "111111111", gender: "MALE", address: "Opendream")
        def m2 = new Member(identificationNumber: "1234567891234", firstname: "Noomz", lastname: "Siriwat", telNo: "111111111", gender: "MALE", address: "Opendream2")

        m1.save()
        m2.save()

        new Policy(key: Policy.KEY_CREDIT_LINE, value: 2000000000).save() // 2 billion baht limit
        new Policy(key: Policy.KEY_INTEREST_METHOD, value: Policy.VALUE_NON_COMPOUND).save()
        new Policy(key: Policy.KEY_INTEREST_RATE_LIMIT, value: '18.00').save()
    }

    @After
    void tearDown() {
        Member.list().each {
            it.delete()
        }

        BalanceTransaction.list().each {
            it.delete()
        }
	}

    void testValidWithdraw() {
        def m1 = Member.findByFirstname("Nat")
        m1.withdraw(100.00)

        m1 = Member.findByFirstname("Nat")
        assert m1.getBalance() == 100.00
    }

    void testValidPay() {
        def m1 = Member.findByFirstname("Nat")
        m1.withdraw(100.00)
        m1.pay(90.00)

        m1 = Member.findByFirstname("Nat")
        assert m1.getBalance() == 10.00
    }

}