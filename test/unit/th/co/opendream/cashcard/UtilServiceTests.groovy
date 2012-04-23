package th.co.opendream.cashcard

import grails.test.mixin.*
import org.junit.*
import org.codehaus.groovy.runtime.typehandling.GroovyCastException
import groovy.time.*
import static java.util.Calendar.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(UtilService)
@Mock([InterestRate])
class UtilServiceTests {
    def today
    @Before
    void setUp() {
        today = Calendar.instance
        today.set 2012, JANUARY, 1
        today = today.time
    }

    void testMoneyRoundUp() {
    	assert service.moneyRoundUp(0.00) == 0.00
    	assert service.moneyRoundUp(100.20) == 100.25
    	assert service.moneyRoundUp(100.34) == 100.50
    	assert service.moneyRoundUp(100.73) == 100.75
    	assert service.moneyRoundUp(100.84) == 101.00
    }

    void testInterestRateEditable() {
        def currentDate = today
    	def existingRate = new InterestRate(
            startDate: currentDate,
            endDate: use(TimeCategory) { currentDate + 10.days },
            rate: 5.00F
        )

        // FAIL if rate.startDate is pass current date
        Calendar.metaClass.static.getInstance = {
            use(TimeCategory) {
                today = today - 1.days
            }
            today.toCalendar()
        }
        assert service.interestRateEditable(existingRate) == false

        // FAIL if rate.startDate is current date
        Calendar.metaClass.static.getInstance = {
            today.toCalendar()
        }
        assert service.interestRateEditable(existingRate) == false

        // PASS if rate.startDate is in the future compare to current date
        Calendar.metaClass.static.getInstance = {
        	use(TimeCategory) {
	        	today = today + 10.days
	        }
            today.toCalendar()
        }
        assert service.interestRateEditable(existingRate) == true

    }
}
