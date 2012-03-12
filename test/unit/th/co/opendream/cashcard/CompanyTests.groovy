package th.co.opendream.cashcard

import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Company)
@Mock (Company)
class CompanyTests {
	@Before
    void setUp() {
    	def existingCompany = new Company(name:'opendream',
                                        address:'bangkok',
                                        taxId:'1234567890')
    	mockForConstraintsTests(Company, [existingCompany])
    }

    void testProperties() {
    	def defaultProps = ["validationSkipMap", "gormPersistentEntity", "properties","id",
                            "gormDynamicFinders", "all", "attached", "class", "constraints", "version",
                            "validationErrorsMap", "errors", "mapping", "metaClass", "count"]
        def companyProps = ['name', 'address', 'taxId', 'dateCreated', 'lastUpdated']

        def instanceProperties = Company.metaClass.properties*.name

        instanceProperties -= defaultProps

        assert 0 == (companyProps - instanceProperties).size()
    }

    void testValidateName() {
    	/*def existingCompany = new Company(name:'opendream',
                                        address:'bangkok',
                                        taxId:'1234567890')
    	mockForConstraintsTests(Company, [existingCompany])*/
    	def field = 'name'
    	
    	def company = new Company()
    	assert field == company.hasProperty(field)?.name

        assert false == company.validate([field])
        assert "nullable" == company.errors[field]

        company.name = 'opendream'
        assert false == company.validate([field])
        assert "unique" == company.errors[field]

        company.name = 'opendreamx'
        assert true == company.validate([field])
    }

    void testValidateAddress() {
    	
    	def field = 'address'
    	
    	def company = new Company()
    	assert field == company.hasProperty(field)?.name

        assert false == company.validate([field])
        assert "nullable" == company.errors[field]

        company.address = 'bangkokx'
        assert true == company.validate([field])
    }

    void testValidateTaxId() {
    	
    	def field = 'taxId'
    	
    	def company = new Company()
    	assert field == company.hasProperty(field)?.name

        assert false == company.validate([field])
        assert "nullable" == company.errors[field]

        company.taxId = '1234567899'
        assert true == company.validate([field])
    }
}