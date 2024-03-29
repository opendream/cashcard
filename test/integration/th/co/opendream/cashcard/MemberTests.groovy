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
	}

	@After
	void tearDown() {
	}

    void testValidWithdraw() {
        def m1 = Member.get(1)
        m1.withdraw(100.00)

        m1 = Member.get(1)
        assert m1.getBalance() == 100.00
        assert BalanceTransaction.count() == 1
    }

    void testValidPay() {
        def m1 = Member.get(1)
        m1.withdraw(100.00)
        m1.pay(100.00)

        m1 = Member.get(1)
        assert m1.getBalance() == 0.00
        assert BalanceTransaction.count() == 2
    }


    void testValidWithdrawButSaveTransactionFail() {
        def count = 0
        BalanceTransaction.metaClass.save = { -> null }

        def m1 = Member.get(1)
        m1.withdraw(100.00)

        m1 = Member.get(1)

        assert count == 1
        assert m1.getBalance() == 0.00
        assert BalanceTransaction.count() == 0
    }

}