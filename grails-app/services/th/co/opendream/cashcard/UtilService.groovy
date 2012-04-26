package th.co.opendream.cashcard

class UtilService {

    static def moneyRoundUp(amount) {
		[0.00, 0.25, 0.50, 0.75, 1.00].collect { it + (amount as BigInteger) }.find { it >= amount }
    }

    static def interestRateEditable(InterestRate rate) {
    	def today = Calendar.instance.time

    	if (today > rate.startDate) {
    		return false
    	}
    	else {
    		return true
    	}
    }

    static def verifyThaiIDCard(id) {

    }
}
