package th.co.opendream.cashcard

import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Users)
@Mock (Users)
class UsersTests {
	@Before
    void setUp() {
    	def existingUsers = new Users(username:'opendream'
                                    	,password: "500"
								        ,enabled: true
										,accountExpired: false
										,accountLocked: false
										,passwordExpired: false)
        
    	mockForConstraintsTests(Users, [existingUsers])
    	//mockDomain(Users, [existingUsers])

    	def existingCompany = new Company(name:'opendream',
                                        address:'bangkok',
                                        taxId:'1234567890')
    	//mockForConstraintsTests(Company, [existingCompany])
    	mockDomain(Company, [existingCompany])
    }

    @After
    void tearDown() {
    	
    }

    void testProperties() {
    	def defaultProps = ["validationSkipMap", "gormPersistentEntity", "properties","id",
                            "gormDynamicFinders", "all", "attached", "class", "constraints", "version",
                            "validationErrorsMap", "errors", "mapping", "metaClass", "count"]
        def userProps = ['username', 'password', 'enabled', 'accountExpired', 'accountLocked', 'passwordExpired', 'company']        

        def instanceProperties = Users.metaClass.properties*.name

        instanceProperties -= defaultProps

        assert 0 == (userProps - instanceProperties).size()
    }

    void testValidateUserName() {
    	
    	def field = 'username'
    	
    	def user = new Users()
    	assert field == user.hasProperty(field)?.name

        assert false == user.validate([field])
        assert "nullable" == user.errors[field]

        user.username = 'opendream'
        assert false == user.validate([field])
        assert "unique" == user.errors[field]

        user.username = 'opendreamx'
        assert true == user.validate([field])
    }

    void testValidatePassword() {
    	
    	def field = 'password'
    	
    	def user = new Users()
    	assert field == user.hasProperty(field)?.name

        assert false == user.validate([field])
        assert "nullable" == user.errors[field]

        user.password = 'password'
        assert true == user.validate([field])
    }

    void testValidateEnable() {
    	
    	def field = 'enabled'
    	
    	def user = new Users()
    	assert field == user.hasProperty(field)?.name

        assert false == user."$field"
        
        user.enabled = true
        assert true == user."$field"
    }

    void testValidateAccountExpired() {
    	
    	def field = 'accountExpired'
    	
    	def user = new Users()
    	assert field == user.hasProperty(field)?.name

        assert false == user."$field"
        
        user.accountExpired = true
        assert true == user."$field"
    }

    void testValidateAccountLocked() {
    	
    	def field = 'accountLocked'
    	
    	def user = new Users()
    	assert field == user.hasProperty(field)?.name

        assert false == user."$field"
        
        user.accountLocked = true
        assert true == user."$field"
    }

    void testValidatePasswordExpired() {
    	
    	def field = 'passwordExpired'
    	
    	def user = new Users()
    	assert field == user.hasProperty(field)?.name

        assert false == user."$field"
        
        user.passwordExpired = true
        assert true == user."$field"
    }

    void testValidateCompany() {
    	
    	def field = 'company'
    	
    	def user = new Users()
    	assert field == user.hasProperty(field)?.name

        assert true == user.validate([field])
        //assert "nullable" == user.errors[field]

        def company = Company.get(1)
        user.company = company
        assert true == user.validate([field])
        assert null != user.company
    }

    void testSave() {
    	// mock Users behavior
    	Users.metaClass.encodePassword = {-> 'password'}
    	Users.metaClass.isDirty = { password-> false}

    	def user = new Users()
    	user.username = 'opendreamx'
    	user.password = 'password'
    	user.enabled = true
    	//user.company = company
        assert true == user.validate()
        //user.save()
        

        // check company & company-users
        assert 1 == Company.count()
        def company = Company.get(1)
        assert null == company.users

        // add user
		company.addToUsers(user)
        assert true == company.validate()
        company.save()        
        // check user and company
        assert 1 == Users.count()
        company = Company.get(1)
        assert 1 == company.users.size()
    }
}