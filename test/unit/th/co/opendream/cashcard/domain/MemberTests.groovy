package th.co.opendream.cashcard.domain



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Member)
class MemberTests {

	void testProperties() {
		def defaultProps = ["validationSkipMap", "gormPersistentEntity", "properties","id", 
							"gormDynamicFinders", "all", "attached", "class", "constraints", "version", 
							"validationErrorsMap", "errors", "mapping", "metaClass", "count"]
		def memberProps = ['identificationNumber', 'firstname', 'lastname', 'dateCreated', 'lastUpdated', 'gender']

		def instanceProperties = Member.metaClass.properties*.name
		instanceProperties -= defaultProps
		
		assertEquals 0 , (instanceProperties - memberProps).size()
		assertEquals 0 , (memberProps - instanceProperties).size()		
	}

    void testValidateIdentificationNumber() { 
       def member = new Member()   
       mockForConstraintsTests(Member, [member]) 
         
       assertFalse member.validate(['identificationNumber'])
       assert "nullable" == member.errors["identificationNumber"]
       member.identificationNumber = '12345v67890'
       assertTrue member.validate(['identificationNumber'])
    }

    void testValidateFirstname() {
    	def member = new Member()
    	assertFalse member.validate(['firstname'])
    	member.firstname = 'firstname'
    	assertTrue member.validate(['firstname'])
    }

    void testValidateLastname() {
    	def member = new Member()
    	assertFalse member.validate(['lastname'])
    	member.lastname = 'lastname'
    	assertTrue member.validate(['lastname'])
    }

    void testValidateGender() {
    	def member = new Member()
    	assertFalse member.validate(['gender'])
    	member.gender = Member.Gender.MALE
    	assertTrue member.validate(['gender'])
    }
}
