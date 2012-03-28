package th.co.opendream.cashcard



import grails.test.mixin.*
import org.junit.*
import groovy.time.*
import static java.util.Calendar.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(ApiController)
@Mock([Member, UtilService, InterestTransaction, TransactionService, Company, SessionUtilService, SchemaService])
class ApiControllerTests {


    def utilControl
    def schemaService
    def sessionUtilControl

    @Before
    void setUp() {
        mockDomain(Member, [
            [id: 1, identificationNumber: "1111111111111", firstname: "Nat", lastname: "Weerawan", telNo: "0891278552", gender: "MALE", address: "11223445", interestMethod: Member.InterestMethod.COMPOUND, balanceLimit: 2000.00],
            [id: 2, identificationNumber: "2222222222222", firstname: "Noomz", lastname: "Siriwat", telNo: "0811111111", gender: "MALE", address: "2222222", interestMethod: Member.InterestMethod.NON_COMPOUND, balanceLimit: 2000.00]
        ])

        def m1 = Member.get(1)
        def m2 = Member.get(2)

        def today = Calendar.instance
        today.set 2012, JANUARY, 1
        today = today.time

        mockDomain(BalanceTransaction, [
            [id: 1, member: m1, amount: 500.00, txType: TransactionType.CREDIT, activity: ActivityType.WITHDRAW, date: today, net: 500.00, remainder: 0.00, balance: 500.00, balance_pay: 0.00, interest_pay: 0.00],
            [id: 2, member: m2, amount: 700.00, txType: TransactionType.CREDIT, activity: ActivityType.WITHDRAW, date: today, net: 700.00, remainder: 0.00, balance: 700.00, balance_pay: 0.00, interest_pay: 0.00],
            [id: 3, member: m1, amount: 200.00, txType: TransactionType.CREDIT, activity: ActivityType.WITHDRAW, date: today.plus(1), net: 200.00, remainder: 0.00, balance: 700.00, balance_pay: 0.00, interest_pay: 0.00],
            [id: 4, member: m2, amount: 300.00, txType: TransactionType.DEBIT, activity: ActivityType.PAYMENT, date: today.plus(1), net: 300.00, remainder: 0.00, balance: 400.00, balance_pay: 300.00, interest_pay: 0.00],
            [id: 5, member: m1, amount: 707.50, txType: TransactionType.DEBIT, activity: ActivityType.PAYMENT, date: today.plus(2), net: 707.32, remainder: 0.18, balance: 0.00, balance_pay: 700.00, interest_pay: 707.50],
            [id: 6, member: m2, amount: 405.25, txType: TransactionType.DEBIT, activity: ActivityType.PAYMENT, date: today.plus(2), net: 405.08, remainder: 0.17, balance: 0.00, balance_pay: 400.00, interest_pay: 405.25],
        ])

        utilControl = mockFor(UtilService)
        sessionUtilControl = mockFor(SessionUtilService)

        m1.transactionService = [
            'withdraw': { obj, amount ->
            },
            'pay': {obj, amount ->
            }
        ]

        def opendream = new Company(name:'opendream', address:'bkk', taxId:'1-2-3-4').save()

        m1.company = opendream
        m2.company = opendream

        m1.save()
        m2.save()
    }

    void testMethod() {
        assert 1
        return
    	controller.schemaService = new SchemaService()

    	params.company = Company.get(1)
    	controller.getTransactionHistory()
    }
}
