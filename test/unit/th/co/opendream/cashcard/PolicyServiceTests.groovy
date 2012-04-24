package th.co.opendream.cashcard

import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(PolicyService)
@Mock([Policy, Company])
class PolicyServiceTests {

	def company, policy

	@Before
	void setUp() {
		company = new Company(name:'opendream', address:'bkk', taxId:'1-2-3-4').save()
        policy = new Policy(key: Policy.KEY_INTEREST_METHOD, value: Policy.VALUE_COMPOUND, company: company)
        company.addToPolicy(policy)
        company.save()
	}

    void testSomething() {
        //
    }

    void testGetCompanyPolicyInterestMethod() {
    	assert service.getInterestMethod(company) == Policy.VALUE_COMPOUND
    }
}
