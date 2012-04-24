package th.co.opendream.cashcard

class PolicyService {
    def isCompoundMethod(Member member) {
    	member.company.policy.findByKey(Policy.KEY_INTEREST_METHOD) == Policy.VALUE_COMPOUND
    }

    def getInterestMethod(Company company) {
    	print company
    	def policy = company.policy.find { it.key == Policy.KEY_INTEREST_METHOD }
    	policy?.value
    }
}
