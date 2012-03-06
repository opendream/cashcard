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
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)

        def count = 0
        BalanceTransaction.metaClass.save = { -> ++count }

    	def m1 = Member.get(1)

    	service.withdraw(m1, 102.00)
        assert Member.get(1).getBalance() == 102.00
        assert count == 1

        service.withdraw(m1, 100.00)
        assert Member.get(1).getBalance() == 202.00
        assert count == 2
    }

    void testWithdrawWithNegativeAmount() {
        shouldFail(RuntimeException) {
            service.withdraw(Member.get(1), -100.00)
        }
    }

    void testWithdrawWithString() {
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)

        def m1 = Member.get(1)

        service.withdraw(m1, "100.00")
        assert m1.getBalance() == 100.00
        assert BalanceTransaction.count() == 1

        service.withdraw(m1, "200.00")
        assert m1.getBalance() == 300.00
        assert BalanceTransaction.count() == 2
    }

    void testWithdrawWithZeroAmount() {
        shouldFail(RuntimeException) {
            service.withdraw(Member.get(1), 0)
        }
    }

    void testValidWithdrawButFailSaveTransaction() {
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)

        def count = 0
        BalanceTransaction.metaClass.save = { -> ++count }

        def m1 = Member.get(1)

        service.withdraw(m1, 3000.00)
        assert Member.get(1).getBalance() == 0.00
        assert count == 0
    }
}
