package th.co.opendream.cashcard.controller

import th.co.opendream.cashcard.domain.Member
import th.co.opendream.cashcard.domain.Member.Gender

import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(MemberController)
@Mock(Member)
class MemberControllerTests {

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

        assert response.redirectedUrl == '/member/show/1'
        assert Member.count() == 1
    }

    void testListMember() {
        controller.list()
        assert Member.list().size() == model.memberList.size()
        assert view == '/member/list'
    }

    void testShowMemberWithId() {
        mockDomain(Member, [
            [id: 1, identificationNumber: "1111111111111", firstname: "Nat", lastname: "Weerawan", telNo: "0891278552", gender: "MALE", address: "11223445"]
        ])
        assert 1 == Member.count()
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
        mockDomain(Member, [
            [id: 1, identificationNumber: "1111111111111", firstname: "Nat", lastname: "Weerawan", telNo: "0891278552", gender: "MALE", address: "11223445"],
            [id: 2, identificationNumber: "2222222222222", firstname: "Noomz", lastname: "Siriwat", telNo: "0811111111", gender: "MALE", address: "2222222"]
        ])

        params.cardId = "1111111111111"
        controller.verifyCard()

        assert response.redirectedUrl == '/member/show/1'
    }

    void testVerifyMemberWithInvalidCardId() {
        mockDomain(Member, [
            [id: 1, identificationNumber: "1111111111111", firstname: "Nat", lastname: "Weerawan", telNo: "0891278552", gender: "MALE", address: "11223445"],
            [id: 2, identificationNumber: "2222222222222", firstname: "Noomz", lastname: "Siriwat", telNo: "0811111111", gender: "MALE", address: "2222222"]
        ])

        params.cardId = "9999999999"
        controller.verifyCard()

        assert view == '/member/verifyCard'
        assert flash.error != null
    }

    void testVerifyMemberWithoutCardId() {
        controller.verifyCard()

        assert view == '/member/verifyCard'
    }
}
