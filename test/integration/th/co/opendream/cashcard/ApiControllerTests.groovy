package th.co.opendream.cashcard

import static org.junit.Assert.*
import org.junit.*
import groovy.time.*
import static java.util.Calendar.*

class ApiControllerTests {
    @Before
    void setUp() {
        /*
        def opendream = new Company(name:'opendream', address:'bkk', taxId:'1-2-3-4')
        def opendreamx = new Company(name:'opendreamx', address:'bkk', taxId:'1-2-3-4')
        def user = new Users(username:'admin', password:'password',
                            , enabled:true, accountExpired:false, accountLocked:false, passwordExpired:false)

        def m1 = new Member(id: 1, identificationNumber: "1111111111111", firstname: "Nat", lastname: "Weerawan", telNo: "0891278552", gender: "MALE", address: "11223445", interestMethod: Member.InterestMethod.COMPOUND, balanceLimit: 2000.00)
        def m2 = new Member(id: 2, identificationNumber: "2222222222222", firstname: "Noomz", lastname: "Siriwat", telNo: "0811111111", gender: "MALE", address: "2222222", interestMethod: Member.InterestMethod.NON_COMPOUND, balanceLimit: 2000.00)

        assert 5 == m1

        opendream.addToUsers(user)
        opendream.addToMembers(m1)
        companyService.save(opendream)

        opendreamx.addToMembers(m2)
        companyService.save(opendreamx)

        def today = Calendar.instance
        today.set 2012, JANUARY, 1
        today = today.time

        new BalanceTransaction(id: 1, member: m1, amount: 500.00, txType: TransactionType.CREDIT, activity: ActivityType.WITHDRAW, date: today, net: 500.00, remainder: 0.00, balance: 500.00, balance_pay: 0.00, interest_pay: 0.00).save()
        new BalanceTransaction(id: 2, member: m2, amount: 700.00, txType: TransactionType.CREDIT, activity: ActivityType.WITHDRAW, date: today, net: 700.00, remainder: 0.00, balance: 700.00, balance_pay: 0.00, interest_pay: 0.00).save()
        new BalanceTransaction(id: 3, member: m1, amount: 200.00, txType: TransactionType.CREDIT, activity: ActivityType.WITHDRAW, date: today.plus(1), net: 200.00, remainder: 0.00, balance: 700.00, balance_pay: 0.00, interest_pay: 0.00).save()
        new BalanceTransaction(id: 4, member: m2, amount: 300.00, txType: TransactionType.DEBIT, activity: ActivityType.PAYMENT, date: today.plus(1), net: 300.00, remainder: 0.00, balance: 400.00, balance_pay: 300.00, interest_pay: 0.00).save()
        new BalanceTransaction(id: 5, member: m1, amount: 707.50, txType: TransactionType.DEBIT, activity: ActivityType.PAYMENT, date: today.plus(2), net: 707.32, remainder: 0.18, balance: 0.00, balance_pay: 700.00, interest_pay: 707.50).save()
        new BalanceTransaction(id: 6, member: m2, amount: 405.25, txType: TransactionType.DEBIT, activity: ActivityType.PAYMENT, date: today.plus(2), net: 405.08, remainder: 0.17, balance: 0.00, balance_pay: 400.00, interest_pay: 405.25).save()
        */
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testSomething() {
        fail "Implement me"
    }
}
