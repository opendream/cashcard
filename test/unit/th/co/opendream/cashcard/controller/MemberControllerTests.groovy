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
    	params.identificationNumber = '1234567890'
    	params.firstname = "The Stand"
        params.lastname = "500"
        params.gender = Gender.MALE

        controller.save()

        assert response.redirectedUrl == '/member/show/1'
        assert flash.message != null
        assert Member.count() == 1
    }
}
