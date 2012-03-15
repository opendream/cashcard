package th.co.opendream.cashcard



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(CompanyController)
@Mock([Company, Users, Member])
class CompanyControllerTests {

	@Before
	void setUp() {
		Users.metaClass.encodePassword = {-> 'password'}
        Users.metaClass.isDirty = { password-> println 'update state!'; false}

		def opendream = new Company(name:'opendream',
        						address:'bangkok',
        						taxId:'1234567890')
        def opendreamx = new Company(name:'opendreamx',
                                address:'chiangmai',
                                taxId:'1234567893')
        def users = [new Users(username:"testUser1", password:"500", enabled:true,
                    accountExpired:false, accountLocked:false, passwordExpired:false),
                    new Users(username:"testUser2", password:"500", enabled:true,
                    accountExpired:false, accountLocked:false, passwordExpired:false),
                    new Users(username:"testUser3", password:"500", enabled:true,
                    accountExpired:false, accountLocked:false, passwordExpired:false)]
        
        users.each {
	        opendream.addToUsers(it)
	    }
	    def members = [new Member(identificationNumber: "1111111111111", firstname: "Nat", lastname: "Weerawan", telNo: "0891278552", gender: "MALE", address: "11223445"),
            new Member(identificationNumber: "2222222222222", firstname: "Noomz", lastname: "Siriwat", telNo: "0811111111", gender: "MALE", address: "2222222")]

	    members.each {
	    	opendream.addToMembers(it)
	    }				
	    opendream.save()
	    opendreamx.save()
        
        def companyService = mockFor(CompanyService)
    	companyService.demand.save(1..1) { company -> company.save(failOnError:true)}
    	controller.companyService = companyService.createMock()        
	}

    @After
    void tearDown() {
        Company.list().each { it.delete(flush:true)}
    }

    void testSearch() {
       def model = controller.search()
       assert null == model
    }

    void testCreate() {
    	def model = controller.create()
    	assert null != model
    	assert model.company instanceof Company
    }

    void testSave() {
    	params.name='opendreamy'
        params.address='bangkok'
        params.taxId='1234567899'
    	
    	controller.save()
    	assert response.redirectedUrl == '/company/edit/3'
    }

    void testSaveFail() {
    	setParams()
    	
    	controller.save()
    	assert view == '/company/create'
    	assert model.company instanceof Company
    	assert model.company.name == 'opendream'
    }

    void testEdit() {
    	params.name='opendream'
        params.id=1

        def model = controller.edit()
        assert model.company.name == 'opendream'
        assert model.users.size() == 3
        assert model.members.size() == 2
    }

    void testEditFail() {
    	params.name='opendreamy'
        params.id=3

        def model = controller.edit()
        assert null == model        
    }

    void testUpdate() {
    	params.id = 1
    	params.version = 0
        params.name='opendreammer'
        params.address='bkk thailand'
        params.taxId='123450000'

    	controller.update()
        assert response.redirectedUrl == '/company/edit/1'
        def opendream = Company.get(1)
        assert 1 == opendream.version
        assert 'opendreammer' == opendream.name
        assert 'bkk thailand' == opendream.address
        assert '123450000' == opendream.taxId
    }

    void testUpdateFailVersionMissmatch() {
        params.id = 1
        params.version = -1

        controller.update()
        assert [:] == model 
    }

    void testUpdateFailUnsave() {
        params.id = 1
        params.version = 0
        params.name=null
        params.address='bkk thailand'
        params.taxId='12345'

        controller.update()
        assert view == '/company/edit'
        assert model.company.name == null
        assert model.users.size() == 3
        assert model.members.size() == 2
    }

    void testCompanySearch() {
        controller.companySearch()
        assert view == '/company/search'
        assert model.results.size() == 2
        
        params.name = 'opendream'
        controller.companySearch()
        assert view == '/company/search'
        assert model.results.size() == 2

        params.name = 'opendream'
        params.address = 'bangkok'
        controller.companySearch()
        assert view == '/company/search'
        assert model.results.size() == 1
    }

    void setParams() {
    	params.name='opendream'        
        params.address='bangkok'
        params.taxId='1234567899'

    }

}
