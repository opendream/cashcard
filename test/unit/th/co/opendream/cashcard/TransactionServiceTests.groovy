
package th.co.opendream.cashcard

import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(TransactionService)
@Mock([Member, BalanceTransaction, Company, SessionUtilService])
class TransactionServiceTests {
    def sessionUtilControl

    def generateFindBy(flag) {
        return { key ->
            if (key == Policy.KEY_CREDIT_LINE) {
                return [value: "2000.00"]
            }
            else {
                return [value: flag]
            }
        }
    }

    @Before
    void setUp() {
        mockDomain(Member, [
            [id: 1, identificationNumber: "1111111111111", firstname: "Nat", lastname: "Weerawan", telNo: "0891278552", gender: "MALE", address: "11223445"],
            [id: 2, identificationNumber: "2222222222222", firstname: "Noomz", lastname: "Siriwat", telNo: "0811111111", gender: "MALE", address: "2222222"]
        ])

        sessionUtilControl = mockFor(SessionUtilService)
    }

    void testValidWithdraw() {
        sessionUtilControl.demand.getCompany(1..10) { -> Company.get(1) }
        service.sessionUtilService = sessionUtilControl.createMock()

        def m1 = Member.get(1)
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)
        def tx = service.withdraw(m1, 100.00)

        assert tx.class == BalanceTransaction
        assert m1.balance == 100.00
        assert BalanceTransaction.count() == 1

        tx = BalanceTransaction.get(1)
        assert tx.amount == 100.00
        assert tx.net == 100.00
        assert tx.remainder == 0.00
        assert tx.activity == ActivityType.WITHDRAW

    }

    void testInvalidWithdraw() {
        sessionUtilControl.demand.getCompany(1..10) { -> Company.get(1) }
        service.sessionUtilService = sessionUtilControl.createMock()

        def m1 = Member.get(1)
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)
        shouldFail(RuntimeException) {
            def tx = service.withdraw(m1, 3000.00)
        }

    }

    void testValidPay() {
        sessionUtilControl.demand.getCompany(1..10) { -> Company.get(1) }
        service.sessionUtilService = sessionUtilControl.createMock()

        def m1 = Member.get(1)
        m1.balance = 100
        m1.interest = 20
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)
        def tx = service.pay(m1, 120.00)

        assert tx.class == BalanceTransaction
        assert m1.balance == 0.00
        assert m1.interest == 0.00
        assert BalanceTransaction.count() == 1

        tx = BalanceTransaction.get(1)
        assert tx.amount == 120.00
        assert tx.net == 120.00
        assert tx.remainder == 0.00
        assert tx.activity == ActivityType.PAYMENT
        assert tx.balance == 0.00
        assert tx.balance_pay == 100.00
        assert tx.interest_pay == 20.00
    }

    void testValidPayWithRemainder() {
        sessionUtilControl.demand.getCompany(1..10) { -> Company.get(1) }
        service.sessionUtilService = sessionUtilControl.createMock()

        def m1 = Member.get(1)
        m1.balance = 100
        m1.interest = 20.12
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)
        def tx = service.pay(m1, 120.25)

        assert tx.class == BalanceTransaction
        assert m1.balance == 0.00
        assert m1.interest == 0.00
        assert BalanceTransaction.count() == 1

        tx = BalanceTransaction.get(1)
        assert tx.amount == 120.25
        assert tx.net == 120.12
        assert tx.remainder == 0.13
    }

    void testInvalidPay() {
        sessionUtilControl.demand.getCompany(1..10) { -> Company.get(1) }
        service.sessionUtilService = sessionUtilControl.createMock()

        def m1 = Member.get(1)
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)

        shouldFail {
            service.pay(m1, 100.50, 300.00)
        }
    }
}