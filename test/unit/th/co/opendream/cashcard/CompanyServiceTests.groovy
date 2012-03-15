package th.co.opendream.cashcard

import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(CompanyService)
@Mock([Company, Users])
class CompanyServiceTests {
	@Before
	void setUp() {
		Users.metaClass.encodePassword = {-> 'password'}
        Users.metaClass.isDirty = { password-> println 'update state!'; false}		
	}

    void testSave() {
    	def schemaService = mockFor(SchemaService)
    	schemaService.demand.create(1..1) { String schema -> }
    	schemaService.demand.with(1..1) { String schema, Closure closure -> }
        service.schemaService = schemaService.createMock()

        def opendream = new Company(name:'opendream',
        						address:'bangkok',
        						taxId:'1234567890')
        def user =  new Users(username:"testUser",
                    password:"500",
                    enabled:true,
                    accountExpired:false,
                    accountLocked:false,
                    passwordExpired:false)
        opendream.addToUsers(user)
        
        service.save(opendream)
        assert 1 == Company.count()
        assert 1 == Users.count()
    }
}
