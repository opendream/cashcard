package th.co.opendream.cashcard

import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(PolicyService)
@Mock([Policy, Company])
class PolicyServiceTests {

	def company

	@Before
	void setUp() {
		company = new Company(name:'opendream', address:'bkk', taxId:'1-2-3-4').save()

        def interestMethodPolicy = new Policy(key: Policy.KEY_INTEREST_METHOD, value: Policy.VALUE_COMPOUND, company: company)
        def creditLinePolicy = new Policy(key: Policy.KEY_CREDIT_LINE, value: "2000.00", company: company)

        company
            .addToPolicy(interestMethodPolicy)
            .addToPolicy(creditLinePolicy)
        company.save()
	}

    void testSomething() {
        //
    }

    void testGetPolicyInstance() {
        def policy = service.getPolicyInstance(company, Policy.KEY_INTEREST_METHOD)
        assert policy.key == Policy.KEY_INTEREST_METHOD
        assert policy.value == Policy.VALUE_COMPOUND

        // NON_COMPOUND
        policy.value = Policy.VALUE_NON_COMPOUND
        policy.save(flush: true)

        policy = service.getPolicyInstance(company, Policy.KEY_INTEREST_METHOD)
        assert policy.key == Policy.KEY_INTEREST_METHOD
        assert policy.value == Policy.VALUE_NON_COMPOUND
    }

    void testIsCompoundMethod() {
        assertTrue service.isCompoundMethod(company)

        // NON_COMPOUND
        def policy = service.getPolicyInstance(company, Policy.KEY_INTEREST_METHOD)
        policy.value = Policy.VALUE_NON_COMPOUND
        policy.save(flush: true)

        assertFalse service.isCompoundMethod(company)
    }

    void testGetCompanyPolicyInterestMethod() {
    	assert service.getInterestMethod(company) == Policy.VALUE_COMPOUND
    }

    void testGetCompanyPolicyCreditLine() {
        assert service.getCreditLine(company) == 2000.00
    }
}
