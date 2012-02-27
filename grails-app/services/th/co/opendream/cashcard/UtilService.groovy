package th.co.opendream.cashcard

class UtilService {

    def moneyRoundUp(amount) {
		[0.00, 0.25, 0.50, 0.75, 1.00].collect { it + (amount as BigInteger) }.find { it >= amount }
    }
}
