package th.co.opendream.cashcard.domain

import th.co.opendream.cashcard.domain.Member.Gender

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
      def field = 'identificationNumber'  
      def existingMember = new Member(identificationNumber:'1234567890', 
                                      firstname:'firstname',
                                      lastname:'lastname',
                                      gender:Gender.MALE)      
      mockForConstraintsTests(Member, [existingMember])

      def member = new Member()
      assert field == member.hasProperty(field)?.name
      
      assertFalse member.validate([field])
      assert "nullable" == member.errors[field]
      
      member.identificationNumber = '1234567890'
      assertFalse member.validate([field])
      assert "unique" == member.errors[field]
      
      member.identificationNumber = '12345v67890'
      assertTrue member.validate([field])
    }

    void testValidateFirstname() {
      def field = 'firstname'  
    	def member = new Member()
      mockForConstraintsTests(Member, [member]) 

      assert field == member.hasProperty(field)?.name
    	assertFalse member.validate([field])
    	member.firstname = 'firstname'
    	assertTrue member.validate([field])
    }

    void testValidateLastname() {
      def field = 'lastname'  
    	def member = new Member()
      mockForConstraintsTests(Member, [member]) 
      
      assert field == member.hasProperty(field)?.name
    	assertFalse member.validate([field])
    	member.lastname = 'lastname'
    	assertTrue member.validate([field])
    }

    void testValidateGender() {
      def field = 'gender'  
    	def member = new Member()
      mockForConstraintsTests(Member, [member]) 
      
      assert field == member.hasProperty(field)?.name
    	assertFalse member.validate([field])
    	member.gender = Member.Gender.MALE
    	assertTrue member.validate([field])
    }
}
