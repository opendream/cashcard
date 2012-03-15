package springsecurity.ui

import static org.junit.Assert.*

import grails.test.mixin.*
//import grails.test.mixin.support.*
import org.junit.*

//import springscurity.ui.UserController
import th.co.opendream.cashcard.Role
import th.co.opendream.cashcard.Users
import th.co.opendream.cashcard.UsersRole

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */

@TestFor(RoleController)
@Mock([Users, Role, UsersRole])
class RoleControllerTests {

	void testEdit() {
		Users.metaClass.encodePassword = {-> 'password'}
        Users.metaClass.isDirty = { password-> println 'update state!'; false}
		def roleUser = new Role(authority:'ROLE_USER').save() 
        def users = [new Users(username:"testUser1", password:"500", enabled:true,
                    accountExpired:false, accountLocked:false, passwordExpired:false),
                    new Users(username:"testUser2", password:"500", enabled:true,
                    accountExpired:false, accountLocked:false, passwordExpired:false),
                    new Users(username:"testUser3", password:"500", enabled:true,
                    accountExpired:false, accountLocked:false, passwordExpired:false),
                    new Users(username:"testUser4", password:"500", enabled:true,
                    accountExpired:false, accountLocked:false, passwordExpired:false),
                    new Users(username:"testUser5", password:"500", enabled:true,
                    accountExpired:false, accountLocked:false, passwordExpired:false),
                    new Users(username:"testUser6", password:"500", enabled:true,
                    accountExpired:false, accountLocked:false, passwordExpired:false),
                    new Users(username:"testUser7", password:"500", enabled:true,
                    accountExpired:false, accountLocked:false, passwordExpired:false),
                    new Users(username:"testUser8", password:"500", enabled:true,
                    accountExpired:false, accountLocked:false, passwordExpired:false),
                    new Users(username:"testUser9", password:"500", enabled:true,
                    accountExpired:false, accountLocked:false, passwordExpired:false),
                    new Users(username:"testUser10", password:"500", enabled:true,
                    accountExpired:false, accountLocked:false, passwordExpired:false),
                    new Users(username:"testUser11", password:"500", enabled:true,
                    accountExpired:false, accountLocked:false, passwordExpired:false)
                    ]
        mockDomain(Users,users)
        
        // add ROLE_USER to testUser
        users.each {
        	UsersRole.create(it, roleUser)
    	}

		params.authority = 'ROLE_USER'
        params.id = 1
		
		def model = controller.edit()
		assert 'ROLE_USER' == model.role.authority 
		assert 10 == model.users.size()
		assert 11 == model.userCount
	}

}