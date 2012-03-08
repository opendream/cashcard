
package th.co.opendream.cashcard

import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(TransactionService)
@Mock([Member, BalanceTransaction])
class TransactionServiceTests {

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
    }

    void testValidWithdraw() {
        def m1 = Member.get(1)
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)
        def tx = service.withdraw(m1, 100.00)

        assert tx.class == BalanceTransaction
        assert m1.balance == 0.00
        assert BalanceTransaction.count() == 1

        tx = BalanceTransaction.get(1)
        assert tx.amount == 100.00
        assert tx.net == 100.00
        assert tx.remainder == 0.00
        assert tx.activity == ActivityType.WITHDRAW

    }

    void testValidPay() {
        def m1 = Member.get(1)
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)
        def tx = service.pay(m1, 100.00, 100.00)

        assert tx.class == BalanceTransaction
        assert m1.balance == 0.00
        assert BalanceTransaction.count() == 1

        tx = BalanceTransaction.get(1)
        assert tx.amount == 100.00
        assert tx.net == 100.00
        assert tx.remainder == 0.00
        assert tx.activity == ActivityType.PAYMENT
    }

    void testValidPayWithRemainder() {
        def m1 = Member.get(1)
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)
        def tx = service.pay(m1, 100.50, 100.34)

        assert tx.class == BalanceTransaction
        assert m1.balance == 0.00
        assert BalanceTransaction.count() == 1

        tx = BalanceTransaction.get(1)
        assert tx.amount == 100.50
        assert tx.net == 100.34
        assert tx.remainder == 0.16
    }

    void testInvalidPay() {
        def m1 = Member.get(1)
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)

        shouldFail {
            service.pay(m1, 100.50, 300.00)
        }
    }
}
