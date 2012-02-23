package th.co.opendream.cashcard.service

import th.co.opendream.cashcard.Member
import th.co.opendream.cashcard.AccountService
import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(AccountService)
@Mock(Member)
class AccountServiceTests {
        @Before
        void setUp() {
            mockDomain(Member, [
                [id: 1, identificationNumber: "1111111111111", firstname: "Nat", lastname: "Weerawan", telNo: "0891278552", gender: "MALE", address: "11223445"],
                [id: 2, identificationNumber: "2222222222222", firstname: "Noomz", lastname: "Siriwat", telNo: "0811111111", gender: "MALE", address: "2222222"]
            ])
        }

        @After
        void tearDown() {
        }

    void testGetInitialBalance() {
        assert service.getBalance(Member.get(1)) == 0.00
        assert service.getBalance(Member.get(2)) == 0.00
    }

    void testGetBalanceType() {
        def accountService = new AccountService()

        assert service.getBalance(Member.get(1)).class == BigDecimal
        assert service.getBalance(Member.get(2)).class == BigDecimal
    }

    void testValidWithdraw() {
        def m1 = Member.get(1)
        assert service.getBalance(m1) == 0.00
        service.withdraw(m1, 100.00)
        assert service.getBalance(m1) == 100.00

        service.withdraw(m1, 100)
        assert service.getBalance(m1) == 200.00
    }

    void testWithdrawWithNegativeAmount() {
        def m1 = Member.get(1)
        shouldFail(RuntimeException) {
                service.withdraw(m1, -100.00)
        }
    }

    void testWithdrawWithString() {
        def m1 = Member.get(1)
        service.withdraw(m1, "100.00")
        assert service.getBalance(m1) == 100.00
    }

    void testWithdrawWithZeroAmount() {
        shouldFail(RuntimeException) {
            service.withdraw(m1, 0)
        }
    }

    void testCanWithdraw() {
        def m1 = Member.get(1)
        assert service.canWithdraw(m1, 100.00) == true
    }

    void testCanWithdrawWithExceedBalance() {
        def m1 = Member.get(1)
        assert service.canWithdraw(m1, 3000.00) == false
    }

    void testCanWithdrawWithNegativeAmount() {
        def m1 = Member.get(1)
        assert service.canWithdraw(m1, -100.00) == false
    }
}
