package springsecurity.ui

import static org.junit.Assert.*

import grails.test.mixin.*
//import grails.test.mixin.support.*
import org.junit.*

//import springscurity.ui.UserController
import th.co.opendream.cashcard.Company
import th.co.opendream.cashcard.Role
import th.co.opendream.cashcard.Users
import th.co.opendream.cashcard.UsersRole

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */

@TestFor(UserController)
@Mock([Users, Role, Company])
class UserControllerTests {

    def springSecurityService = [:]

	@Before
    void setUp() {
        
        grailsApplication.config.company.management.authority = 'ROLE_ADMIN'
        def opendream = new Company(name:'opendream',
        						address:'bangkok',
        						taxId:'1234567890')
        def opendreamx = new Company(name:'opendreamx',
                                address:'bangkok',
                                taxId:'1234567893')
                                
        //company.save()
        mockDomain(Company, [opendream, opendreamx])
        def roleUser = new Role(authority:'ROLE_USER') 
        def roleAdmin = new Role(authority:'ROLE_ADMIN')
        def user =  new Users(username:"testUser",
                    password:"500",
                    enabled:true,
                    accountExpired:false,
                    accountLocked:false,
                    passwordExpired:false)
        mockDomain(Role, [roleUser, roleAdmin])
        mockDomain(Users,[user])
        mockDomain(UsersRole)

        // add ROLE_USER to testUser
        UsersRole.create(user, roleUser)

        // add testUser to opendream(Company)
        opendream.addToUsers(user)

        // mock springSecurityUiService.encodePassword
        def springSecurityUiService = [:]
        springSecurityUiService.encodePassword = {salt, password -> '1234'}
        controller.springSecurityUiService = springSecurityUiService

        // stub springSecurityService principal
        def principal = [:]
        principal.authorities = ['ROLE_ADMIN', 'ROLE_USER']
        principal.companyId = 1
        principal.companyName = 'opendreamx'
        springSecurityService.principal = principal
        controller.springSecurityService = springSecurityService

        // mock userCache
        def userCache = [:]
        userCache.removeUserFromCache = {str -> null}
        controller.userCache = userCache

        // mock UserController lookupUserClass
        UserController.metaClass.lookupUserClass = { -> Users}
        // mock UserController lookupRoleClass
        UserController.metaClass.lookupRoleClass = { -> Role}
        // mock UsersRole static removeAll
        UsersRole.metaClass.static.removeAll = { Users users -> UsersRole.list().each {it.delete() } }
        // mock UserController lookupUserRoleClass
        UserController.metaClass.lookupUserRoleClass = { -> UsersRole }
        // mock Users encodePassword
        Users.metaClass.encodePassword = {-> 'password'}
        // mock Users isDirty
        Users.metaClass.isDirty = { password-> println 'update state!'; false}
    }

    @After
    void tearDown() {
        Users.list().each { it.delete() }        
    }

    void testCreate() {
        def model = controller.create()

        assert model.user != null
        assert model.authorityList.size() == 2
        assert model.companyList.size() == 2
        
        springSecurityService.principal.authorities = ['ROLE_USER']
        //controller.springSecurityService = springSecurityService
        model = controller.create()
        assert model.user != null
        assert model.authorityList.size() == 2
        assert model.companyList.size() == 1
    }

    void testSave() {
        // login user.principal.companyName = 'opendreamx'
        setParams()
	params.'ROLE_USER' = 'on'
	params.companyId = 2

	controller.save()
        assert null != Users.get(1).authorities.find {it.authority == 'ROLE_USER'}
	assert 1 == Company.get(2).users.size()

        assert 2 == Users.count()
        assert response.redirectedUrl == '/user/edit/2'
    }

    void testSaveWithOutCompanyIdAndRole() {
        // login user.principal.companyName = 'opendreamx'
        setParams()

        controller.save()
        assert 1 == Users.count()
        assert null == Company.get(2).users
        assert view == '/user/create'
    }

    void testSaveWithOutCompanyId() {
        // login user.principal.companyName = 'opendreamx'
        setParams()
        params.'ROLE_USER' = 'on'        

        controller.save()
        assert 1 == Users.count()
        assert null == Company.get(2).users
        assert view == '/user/create'
    }

    void testSaveWithOutRole() {
        // login user.principal.companyName = 'opendreamx'
        setParams()
        params.companyId = 2

        controller.save()
        assert 1 == Users.count()
        assert null == Company.get(2).users
        assert view == '/user/create'
    }

    void testEdit() {
        // start edit
        params.username = "testUser"
        params.id = 1
        def model = controller.edit()
        assert "testUser" == model.user.username
        assert 2 == model.roleMap.size()
        assert model.companyList.size() == 2

        springSecurityService.principal.authorities = ['ROLE_USER']
        params.id = 1
        model = controller.edit()
        assert "testUser" == model.user.username
        assert 2 == model.roleMap.size()
        assert model.companyList.size() == 1
    }

    void testUpdate() {  
        assert 1 == Company.get(1).users.size()      
        // start update
        params.id = 1
        params.version = 0
        params.username = "testUser"
        params.password = "501"
        params.enabled = false
        params.accountExpired = false
        params.accountLocked = false
        params.passwordExpired = false
        params.'ROLE_ADMIN' = 'on'
        params.companyId = 2
        controller.update()

        assert response.redirectedUrl == '/user/edit/1'
        assert 1 == Company.get(2).users.size()
        assert 0 == Company.get(1).users.size()
        assert false == Users.get(1).enabled
    }

    void testUpdateFail() {        
        // start update
        params.id = 1
        params.version = 0
        params.username = "testUser"
        params.password = null
        params.enabled = true
        params.accountExpired = false
        params.accountLocked = false
        params.passwordExpired = false
        params.'ROLE_ADMIN' = 'on'
        params.companyId = 1
        controller.update()

        assert view == '/user/edit'
        assert model.user.username == 'testUser'
    }

    void setParams() {
        params.username = "user"
        params.password = "500"
        params.enabled = true
        params.accountExpired = false
        params.accountLocked = false
        params.passwordExpired = false
    }
}
