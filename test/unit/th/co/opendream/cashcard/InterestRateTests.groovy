package th.co.opendream.cashcard

import grails.test.mixin.*
import org.junit.*
import org.codehaus.groovy.runtime.typehandling.GroovyCastException
import groovy.time.*
import static java.util.Calendar.*
/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(InterestRate)
class InterestRateTests {
    def today
    @Before
    void setUp() {
        today = Calendar.instance
        today.set 2012, JANUARY, 1
        today = today.time
    }

    void testProperties() {
        def memberProps = ['startDate', 'rate', 'dateCreated', 'lastUpdated']
        def instanceProperties = InterestRate.metaClass.properties*.name
        assert 0 == (memberProps - instanceProperties).size()
    }

    void testPositiveInterestRate() {
        def field = 'rate'
        mockForConstraintsTests(InterestRate)
        def rate = new InterestRate()

        rate.rate = 5.00F
        assertTrue rate.validate([field])
    }

    void testNegativeInterestRate() {
        def field = 'rate'
        mockForConstraintsTests(InterestRate)
        def rate = new InterestRate()

        rate.rate = -5.00F
        assertFalse rate.validate([field])
    }

    void testInterestRateEmptyRate() {
        def field = 'rate'
        mockForConstraintsTests(InterestRate)
        def rate = new InterestRate()

        assertFalse rate.validate([field])
        shouldFail(GroovyCastException) {
            rate.rate = ''
        }
        assertFalse rate.validate([field])
    }

    void testInterestRateEmptyStartDate() {
        def field = 'startDate'
        mockForConstraintsTests(InterestRate)
        def rate = new InterestRate()

        assert rate.validate([field]) == false
        shouldFail(GroovyCastException) {
            rate.startDate = ''
        }
        assertFalse rate.validate([field])
    }

    void testStartDateUnique() {
        def field = 'startDate'
        def currentDate = today
        def existingRate = new InterestRate(
            startDate: currentDate,
            endDate: use(TimeCategory) { currentDate + 10.days },
            rate: 5.00F
        )
        mockForConstraintsTests(InterestRate, [existingRate])

        def rate = new InterestRate()
        rate.startDate = currentDate
        assertFalse rate.validate([field])
        assert "unique" == rate.errors[field]
    }

}