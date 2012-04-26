package th.co.opendream.cashcard



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(MemberHistory)
@Mock(Member)
class MemberHistoryTests {

    void testProperties() {
    	def memberProps = ['identificationNumber', 'firstname', 'lastname', 'dateCreated', 'lastUpdated', 'gender', 'telNo', 'address', 'balance', 'member', 'status']
        def instanceProperties = MemberHistory.metaClass.properties*.name

        assert 0 == (memberProps - instanceProperties).size()
    }

    void testUniqueNotRestrict() {
    	def m1 = new Member(identificationNumber: "1111111111111", firstname: "Nat", lastname: "Weerawan", telNo: "0891278552", gender: "MALE", address: "11223445")
    	m1.save()

    	new MemberHistory(identificationNumber: "1111111111111", firstname: "Nat", lastname: "Weerawan", telNo: "0891278552", gender: "MALE", address: "11223445", member: m1, status: Member.Status.ACTIVE).save()
    	new MemberHistory(identificationNumber: "1111111111111", firstname: "Nat", lastname: "Weerawan", telNo: "0891278552", gender: "MALE", address: "11223445", member: m1, status: Member.Status.ACTIVE).save()

    	assert MemberHistory.count() == 2
    }

}
