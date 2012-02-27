package th.co.opendream.cashcard



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(UtilService)
class UtilServiceTests {

    void testMoneyRoundUp() {
    	assert service.moneyRoundUp(0.00) == 0.00
    	assert service.moneyRoundUp(100.20) == 100.25
    	assert service.moneyRoundUp(100.34) == 100.50
    	assert service.moneyRoundUp(100.73) == 100.75
    	assert service.moneyRoundUp(100.84) == 101.00
    }
}
