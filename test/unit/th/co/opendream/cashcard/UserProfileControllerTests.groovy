package th.co.opendream.cashcard



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(UserProfileController)
@Mock([Company, Users])
class UserProfileControllerTests {

	def springSecurityService = [:]

	@Before
	void setUp() {
		Users.metaClass.encodePassword = {-> 'password'}
        Users.metaClass.isDirty = { password-> println 'update state!'; false}

		def opendream = new Company(name:'opendream',
        						address:'bangkok',
        						taxId:'1234567890')
        
        def users = [new Users(username:"testUser1", password:"500", enabled:true,
                    accountExpired:false, accountLocked:false, passwordExpired:false),
                    new Users(username:"testUser2", password:"500", enabled:true,
                    accountExpired:false, accountLocked:false, passwordExpired:false),
                    new Users(username:"testUser3", password:"500", enabled:true,
                    accountExpired:false, accountLocked:false, passwordExpired:false)]
        
        users.each {
	        opendream.addToUsers(it)
	    }
	    
	    opendream.save()	
	    
	    // stub springSecurityService principal
        def principal = [:]
        principal.username = "testUser1"
        principal.id = Users.findByUsername("testUser1").id
        principal.authorities = ['ROLE_ADMIN', 'ROLE_USER']
        principal.companyId = 1
        principal.companyName = 'opendreamx'
        springSecurityService.principal = principal
        springSecurityService.encodePassword = {String password -> 'newpassword'}
        controller.springSecurityService = springSecurityService          
	}

    void testShow() {
       def model = controller.show()
       assert model != null
       assert model.id == springSecurityService.principal.id       
    }

    void testEdit() {
       def model = controller.show()
       assert model != null
       assert model.id == springSecurityService.principal.id
    }

    void testUpdate() {
    	params.password='newpassword'

        controller.update() 

        assert response.redirectedUrl == '/userProfile/show'
    }

    void testUpdateFail() {
    	Users.metaClass.save = {Map map-> false}
    	params.id = springSecurityService.principal.id
        params.password = 'password'

        controller.update() 

        assert response.redirectedUrl == '/userProfile/edit'
    }
}
