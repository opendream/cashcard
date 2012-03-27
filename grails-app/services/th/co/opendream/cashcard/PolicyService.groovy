package th.co.opendream.cashcard

class PolicyService {
    def isCompoundMethod(Member member) {
    	member.interestMethod == Member.InterestMethod.COMPOUND
    }
}
