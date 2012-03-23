package th.co.opendream.cashcard

class PolicyService {

    static BigDecimal getGlobalFinancialAmountLimit() {
        return 2000.00
    }

    def isCompoundMethod(Member member) {
    	member.interestMethod == Member.InterestMethod.COMPOUND
    }
}
