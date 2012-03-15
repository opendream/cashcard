package th.co.opendream.cashcard


import th.co.opendream.cashcard.Member.Gender
import th.co.opendream.cashcard.Member.Status
import grails.test.mixin.*
import org.junit.*
import groovy.time.*
import static java.util.Calendar.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(MemberController)
@Mock([Member, UtilService, InterestTransaction, TransactionService, MemberService])
class MemberControllerTests {

    def utilControl
    def memberControl

    def generateFindBy(flag) {
        return {key ->
            if (Policy.KEY_CREDIT_LINE) {
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


        def m1 = Member.get(1)
        def m2 = Member.get(2)

        def today = Calendar.instance
        today.set 2012, JANUARY, 1
        today = today.time

        mockDomain(BalanceTransaction, [
            [id: 1, member: m1, amount: 500.00, txType: TransactionType.CREDIT, activity: ActivityType.WITHDRAW, date: today, net: 500.00, remainder: 0.00],
            [id: 2, member: m2, amount: 700.00, txType: TransactionType.CREDIT, activity: ActivityType.WITHDRAW, date: today, net: 700.00, remainder: 0.00],
            [id: 3, member: m1, amount: 200.00, txType: TransactionType.CREDIT, activity: ActivityType.WITHDRAW, date: today.plus(1), net: 200.00, remainder: 0.00],
            [id: 4, member: m2, amount: 300.00, txType: TransactionType.DEBIT, activity: ActivityType.PAYMENT, date: today.plus(1), net: 300.00, remainder: 0.00],
            [id: 5, member: m1, amount: 707.50, txType: TransactionType.DEBIT, activity: ActivityType.PAYMENT, date: today.plus(2), net: 707.32, remainder: 0.18],
            [id: 6, member: m2, amount: 405.25, txType: TransactionType.DEBIT, activity: ActivityType.PAYMENT, date: today.plus(2), net: 405.08, remainder: 0.17],
        ])

        utilControl = mockFor(UtilService)
        memberControl = mockFor(MemberService)

        m1.transactionService = [
            'withdraw': { obj, amount ->
            },
            'pay': {obj, amount ->
            }
        ]
    }

    @After
    void tearDown() {
    }

    void testCreate() {
    	controller.create()

    	assert model.memberInstance != null
    	assert view == '/member/create'
    }

    void testSaveInvalidMember() {
    	controller.save()

        assert model.memberInstance != null
        assert view == '/member/create'
    }

    void testSaveValidMember() {
    	params.identificationNumber = '1234567890123'
    	params.firstname = "The Stand"
        params.lastname = "500"
        params.gender = "MALE"
        params.telNo = '0891278551'
        params.address = "Opendream"

        controller.save()

        assert Member.count() == 3
        assert response.redirectedUrl == '/member/show/3'
    }

    void testListMember() {
        controller.list()
        assert Member.list().size() == model.memberList?.size()
        assert view == '/member/list'
    }

    void testShowMemberWithId() {
        assert 2 == Member.count()
        params.id = 1
        controller.show()
        assert view == '/member/show'
        assert model.memberInstance != null
    }

    void testShowMemberWithoutId() {
        controller.show()
        assert response.redirectedUrl == '/error'
    }

    void testVerifyMemberWithValidCardId() {
        params.cardId = "1111111111111"
        controller.verifyCard()

        assert response.redirectedUrl == '/member/show/1'
    }

    void testVerifyMemberWithInvalidCardId() {
		// First submit
        params.cardId = "9999999999"
        controller.verifyCard()
        assert view == '/member/verifyCard'
        assert flash.error != null

        // Second submit without cardId must have no error message.
        // (prevent flash scope variable stuck in session.)
        params.cardId = ""
        controller.verifyCard()
        assert view == '/member/verifyCard'
        assert flash.error == null

        // Third submit, everything will be in normal state(no errors)
        params.cardId = ""
        controller.verifyCard()
        assert view == '/member/verifyCard'
        assert flash.error == null
    }

    void testVerifyMemberWithoutCardId() {
        controller.verifyCard()

        assert view == '/member/verifyCard'
    }

    void testMemberWithdrawValidUid() {
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_COMPOUND)

        params.amount = '100.00'
        params.id = '1'

        controller.withdraw()

        assert response.redirectedUrl == '/member/show/1'
    }

    void testMemberWithdrawWithInvalidUid() {
        params.amount = '200.00'
        params.id = '999999999'

        controller.withdraw()

        assert response.redirectedUrl == '/error'
    }

    void testMemberInvalidWithdrawAmount() {
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_COMPOUND)

        params.amount = '10000'
        params.id = '1'

        controller.withdraw()


        assert flash.error != null
        assert view == '/member/withdraw'
    }

    void testMemberPaymentEmptyId() {
        def model = controller.payment()

        assert response.redirectedUrl == '/error'
    }

    void testMemberPayment() {
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_COMPOUND)

        utilControl.demand.moneyRoundUp(1..1) { amount -> 200.25 }
        controller.utilService = utilControl.createMock()

        params.id = '1'
        def model = controller.payment()

        utilControl.verify()
        assert model.memberInstance != null
    }

    void testMemberValidPayWithNoChange() {
        params.id = '1'
        params.amount = '200.00'
        params.net = '200.00'
        def model = controller.pay()

        assert flash.message != null
        assert response.redirectedUrl == '/member/show/1'

    }

    void testMemberValidPayWithChange() {
        params.id = '1'
        params.amount = '200.00'
        params.net = '220.00'
        def model = controller.pay()

        assert flash.message != null
        assert response.redirectedUrl == '/member/show/1'
    }

    void testMemberInvalidPay() {
        params.id = '1'
        def model = controller.pay()

        assert flash.message != null
        assert response.redirectedUrl == '/member/payment/1'
    }

    void testMemberSearchByIdCard() {
        params.identificationNumber = '1111111111111'
        controller.list()

        assert model.memberList.size() == 1
        assert view == '/member/list'
        response.reset()

        params.identificationNumber = '2222222222222'
        controller.list()

        assert model.memberList.size() == 1
        assert view == '/member/list'
        response.reset()
    }

    void testMemberSearchByFirstname() {
        params.firstname = 'Nat'
        controller.list()

        assert model.memberList.size() == 1
        assert view == '/member/list'
        response.reset()

        params.firstname = 'nat'
        controller.list()

        assert model.memberList.size() == 1
        assert view == '/member/list'
        response.reset()

        params.firstname = 'n'
        controller.list()

        assert model.memberList.size() == 2
        assert view == '/member/list'
    }

    void testMemberSearchByLastname() {
        params.lastname = 'Weerawan'
        controller.list()

        assert model.memberList.size() == 1
        assert view == '/member/list'
        response.reset()

        params.lastname = 'Weerawan'
        controller.list()

        assert model.memberList.size() == 1
        assert view == '/member/list'
        response.reset()

        params.lastname = 'w'
        controller.list()

        assert model.memberList.size() == 2
        assert view == '/member/list'
    }

    void testMemberSearchByTelephone() {
        params.telNo = '0891278552'
        controller.list()

        assert model.memberList.size() == 1
        assert view == '/member/list'
        response.reset()

        params.telNo = '08'
        controller.list()

        assert model.memberList.size() == 2
        assert view == '/member/list'
    }

    void testMemberSearchCompound() {
        params.identificationNumber = '1111111111111'
        params.firstname = 'Nat'
        params.lastname = 'Weerawan'
        params.telNo = '0891278552'

        controller.list()

        assert model.memberList.size() == 1
        assert model.memberCount == 1
        assert view == '/member/list'
    }

    void testMemberTransactionList() {
        controller.transaction()
        assert response.redirectedUrl == '/error'
        response.reset()

        params.id = 6789
        controller.transaction()
        assert response.redirectedUrl == '/error'
        response.reset()

        params.id = 1
        controller.transaction()

        assert model.transactionList.size() == 3
        assert model.transactionCount == 3
        assertNotNull model.memberInstance
        assert view == '/member/transaction'
    }

    void testMemberTransactionListSortByDate() {
        params.id = 1
        controller.transaction()

        def tx1 = model.transactionList[0]
        def tx2 = model.transactionList[1]
        def tx3 = model.transactionList[2]

        assert tx3.date <= tx2.date && tx2.date <= tx1.date
    }

    void testEditInvalidMember() {
        controller.edit()

        assert response.redirectedUrl == '/error'
    }

    void testEditValidMember() {
        params.id = 1
        controller.edit()

        assert view == '/member/edit'
    }

    void testUpdateInvalidMember() {
        controller.update()

        assert response.redirectedUrl == '/error'
    }

    def populateValidParams(params) {
        params.firstname = 'Siriwat'
        params.lastname = 'Uamngamsup'
        params.telNo = '0846401342'
        params.address = '162/37'
    }

    void testUpdateValidValue() {
        memberControl.demand.update(1..1) { member -> true }
        controller.memberService = memberControl.createMock()

        populateValidParams(params)
        params.id = 2
        params.version = 1

        controller.update()
        memberControl.verify()


        assert flash.message != null
        assert response.redirectedUrl == '/member/show/2'
    }

    void testUpdateWithOutdatedVersionNumber() {
        def m1 = Member.get(2)
        m1.version = 1
        m1.save()

        populateValidParams(params)
        params.id = 2
        params.version = -2

        controller.update()

        assert view == '/member/edit'
        assert model.memberInstance != null
        assert model.memberInstance.errors.getFieldError('version')
    }

    void testDisableSuccess() {
        memberControl.demand.update(1..1) { member -> true }
        params.id = 2

        controller.memberService = memberControl.createMock()
        controller.disable()
        memberControl.verify()

        assert flash.message != null
        assert model.errors == null
        assert response.redirectedUrl == '/member/list'
    }

    void testDisableFail() {
        memberControl.demand.update(1..1) { member -> false }
        params.id = 2

        controller.memberService = memberControl.createMock()
        controller.disable()
        memberControl.verify()

        assert flash.message != null
        assert model.memberInstance.errors != null
        assert view == '/member/edit'
    }

}