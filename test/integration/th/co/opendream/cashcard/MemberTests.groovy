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


    void testValidWithdraw() {
        Policy.metaClass.static.findByKey = generateFindBy()

        def count = 0
        BalanceTransaction.metaClass.save = { -> ++count }

        def m1 = Member.get(1)

        m1.transactionService = transactionService
        m1.policyService = policyService


        m1.withdraw(102.00)

        assert Member.get(1).getBalance() == 102.00
        assert count == 1

        m1.withdraw(100.00)
        assert Member.get(1).getBalance() == 202.00
        assert count == 2
    }

    void testWithdrawWithNegativeAmount() {
        shouldFail(RuntimeException) {
            Member.get(1).withdraw(-100.00)
        }
    }

    void testWithdrawWithZeroAmount() {
        shouldFail(RuntimeException) {
            Member.get(1).withdraw(0)
        }
    }

    void testInvalidWithdraw() {
        Policy.metaClass.static.findByKey = generateFindBy()

        def count = 0
        BalanceTransaction.metaClass.save = { -> ++count }

        def m1 = Member.get(1)
        m1.transactionService = transactionService

        shouldFail(RuntimeException) {
                m1.withdraw(3000.00)
        }
        assert Member.get(1).getBalance() == 0.00
        assert count == 0
    }

    void testPayWithRoundup() {
        // policy return NON_COMPOUND
        Policy.metaClass.static.findByKey = generateFindBy()

        // นับจำนวนว่าเกิด balance transaction กี่รายการ
        def count = 0
        BalanceTransaction.metaClass.save = { -> ++count}

        def m1 = Member.get(1)
        m1.transactionService = transactionService
        m1.utilService = utilService
        m1.policyService = policyService

        m1.balance = 100.00
        m1.interest = 10.13
        def bal = m1.pay(110.25)

        assert m1.balance == 0.00
        assert m1.interest == 0.00
        assert count == 1
        assert bal.remainder == 0.12

        m1.balance = 110.13
        m1.interest = 10.13
        bal = m1.pay(110.25)

        assert m1.balance == 0.00
        assert m1.interest == 0.00
        assert count == 2
        assert bal.remainder == 0.12

        /* Balance 200.00 */
        m1.balance = 200.00
        m1.interest = 23.49
        bal = m1.pay(223.50)

        assert m1.balance == 0.00
        assert m1.interest == 0.00
        assert bal.remainder == 0.01
        assert count == 3

        m1.balance = 223.17
        m1.interest = 23.25
        bal = m1.pay(223.25)

        assert m1.balance == 0.00
        assert m1.interest == 0.00
        assert bal.remainder == 0.08
        assert count == 4
    }


    void testPayWithAllDebt() {
        Policy.metaClass.static.findByKey = generateFindBy()

        def count = 0
        BalanceTransaction.metaClass.save = { -> ++count }

        def m1 = Member.get(1)
        m1.transactionService = transactionService
        m1.utilService = utilService
        m1.policyService = policyService

        m1.balance = 100.00
        m1.interest = 10.00
        m1.pay(110.00)

        assert m1.balance == 0.00
        assert m1.interest == 0.00
        assert count == 1

        m1.balance = 110.00
        m1.interest = 10.00
        m1.pay(110.00)

        assert m1.balance == 0.00
        assert m1.interest == 0.00
        assert count == 2

        /* Balance 200.00 */
        m1.balance = 200.00
        m1.interest = 23.00
        m1.pay(223.00)

        assert m1.balance == 0.00
        assert m1.interest == 0.00
        assert count == 3

        m1.balance = 223.00
        m1.interest = 23.00
        m1.pay(223.00)

        assert m1.balance == 0.00
        assert m1.interest == 0.00
        assert count == 4
    }

    void testPayWithPartialDebt() {
        def m1 = Member.get(1)
        m1.transactionService = transactionService
        m1.utilService = utilService
        m1.policyService = policyService


        Policy.metaClass.static.findByKey = generateFindBy()

        def count = 0
        BalanceTransaction.metaClass.save = { -> ++count }

        m1.balance = 100.00
        m1.interest = 10.00
        m1.pay(90.00)

        assert m1.balance == 20.00
        assert m1.interest == 0.00
        assert count == 1


        m1.balance = 110.00
        m1.interest = 10.00
        m1.pay(90.00)

        assert m1.balance == 20.00
        assert m1.interest == 0.00
        assert count == 2

        /* Balance 200.00 */
        m1.balance = 200.00
        m1.interest = 23.00
        m1.pay(123.00)

        assert m1.balance == 100.00
        assert m1.interest == 0.00
        assert count == 3

        m1.balance = 223.00
        m1.interest = 23.00
        m1.pay(223.00)

        assert m1.balance == 0.00
        assert m1.interest == 0.00
        assert count == 4
    }

    void testPayWithFullInterest() {
        def m1 = Member.get(1)
        m1.transactionService = transactionService
        m1.utilService = utilService


        Policy.metaClass.static.findByKey = generateFindBy()

        def count = 0
        BalanceTransaction.metaClass.save = { -> ++count }

        m1.balance = 100.00
        m1.interest = 10.00
        m1.pay(10.00)

        assert m1.balance == 100.00
        assert m1.interest == 0.00
        assert count == 1

        m1.balance = 110.00
        m1.interest = 10.00
        m1.pay(10.00)

        assert m1.balance == 100.00
        assert m1.interest == 0.00
        assert count == 2

        /* Balance 200.00 */
        m1.balance = 200.00
        m1.interest = 23.00
        m1.pay(23.00)

        assert m1.balance == 200.00
        assert m1.interest == 0.00
        assert count == 3

        m1.balance = 223.00
        m1.interest = 23.00
        m1.pay(23.00)

        assert m1.balance == 200.00
        assert m1.interest == 0.00
        assert count == 4

    }

    void testPayWithPartialInterest() {
        def m1 = Member.get(1)
        m1.transactionService = transactionService
        m1.utilService = utilService
        m1.policyService = policyService

        def count = 0
        BalanceTransaction.metaClass.save = { -> ++count }

        m1.balance = 100.00
        m1.interest = 10.00
        m1.pay(3.00)

        assert m1.balance == 100.00
        assert m1.interest == 7.00
        assert count == 1

        Policy.metaClass.static.findByKey = generateFindBy()
        m1.balance = 110.00
        m1.interest = 10.00
        m1.pay(3.00)

        assert m1.balance == 107.00
        assert m1.interest == 7.00
        assert count == 2

        /* Balance 200.00 */
        m1.balance = 200.00
        m1.interest = 23.00
        m1.pay(3.00)

        assert m1.balance == 200.00
        assert m1.interest == 20.00
        assert count == 3

        m1.balance = 223.00
        m1.interest = 23.00
        m1.pay(3.00)

        assert m1.balance == 220.00
        assert m1.interest == 20.00
        assert count == 4
    }

}