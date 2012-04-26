package th.co.opendream.cashcard



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(MemberService)
@Mock([Member, MemberHistory])
class MemberServiceTests {

	@Before
	void setUp() {
        mockDomain(Member, [
            [id: 1, identificationNumber: "1111111111111", firstname: "Nat", lastname: "Weerawan", telNo: "0891278552", gender: "MALE", address: "11223445"]
        ])
	}

    void testUpdate() {
    	def m1 = Member.get(1)
    	service.update(m1)

    	assert Member.count() == 1
    	assert MemberHistory.count() == 1

    	service.update(m1)
    	assert Member.count() == 1
    	assert MemberHistory.count() == 2
    }
}
