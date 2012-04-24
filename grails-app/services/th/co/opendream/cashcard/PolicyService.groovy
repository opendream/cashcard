package th.co.opendream.cashcard

class PolicyService {

	def getPolicyInstance(Company company, key) {
		company.policy.find { it.key == key }
	}

    def isCompoundMethod(Company company) {
    	Policy.VALUE_COMPOUND == getInterestMethod(company)
    }

    def getInterestMethod(Company company) {
    	def policy = getPolicyInstance(company, Policy.KEY_INTEREST_METHOD)
    	policy?.value
    }

    def getCreditLine(Company company) {
        getPolicyInstance(company, Policy.KEY_CREDIT_LINE)?.value as BigDecimal
    }
}
