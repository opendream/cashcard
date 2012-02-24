package th.co.opendream.cashcard


import th.co.opendream.cashcard.Member.Gender
import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(MemberController)
@Mock(Member)
class MemberControllerTests {

    def accountControl

    @Before
    void setUp() {
        mockDomain(Member, [
            [id: 1, identificationNumber: "1111111111111", firstname: "Nat", lastname: "Weerawan", telNo: "0891278552", gender: "MALE", address: "11223445"],
            [id: 2, identificationNumber: "2222222222222", firstname: "Noomz", lastname: "Siriwat", telNo: "0811111111", gender: "MALE", address: "2222222"]
        ])

        accountControl = mockFor(AccountService)
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
        assert Member.list().size() == model.memberList.size()
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
        accountControl.demand.canWithdraw(1..1) { Member member, amount -> true }
        accountControl.demand.withdraw(1..1) { Member member, amount -> true }
        controller.accountService = accountControl.createMock()

        params.amount = 100.00
        params.id = 1

        controller.withdraw()
        accountControl.verify()

        assert response.redirectedUrl == '/member/show/1'
    }

    void testMemberWithdrawWithInvalidUid() {
        params.amount = 200.00
        params.id = 999999999

        controller.withdraw()

        assert response.redirectedUrl == '/error'
    }

    void testMemberInvalidWithdrawAmount() {
        accountControl.demand.canWithdraw(1..1) { Member member, amount -> false }
        accountControl.demand.withdraw(0..0) { Member member, amount -> true }
        controller.accountService = accountControl.createMock()

        params.amount = 10000
        params.id = 1

        controller.withdraw()

        accountControl.verify()

        assert flash.error != null
        assert view == '/member/withdraw'

    }

    void testMemberPaymentEmptyId() {
        def model = controller.payment()

        assert response.redirectedUrl == '/error'
    }

    void testMemberPayment() {
        params.id = 1
        def model = controller.payment()

        assert model.memberInstance != null
    }

    void testMemberValidPayWithNoChange() {
        accountControl.demand.pay(1..1) { Member member, amount -> [:] }
        controller.accountService = accountControl.createMock()

        params.id = 1
        params.amount = 200.00
        def model = controller.pay()

        accountControl.verify()
        assert flash.message != null
        assert response.redirectedUrl == '/member/show/1'

    }

    void testMemberInvalidPay() {
        accountControl.demand.pay(1..1) { Member member, amount -> [errors:["Can't pay."]] }
        controller.accountService = accountControl.createMock()

        params.id = 1
        def model = controller.pay()

        accountControl.verify()
        assert flash.message != null
        assert response.redirectedUrl == '/member/payment/1'

    }

}