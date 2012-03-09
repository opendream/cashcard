package th.co.opendream.cashcard

import grails.test.mixin.*
import org.junit.*
import groovy.time.*
import static java.util.Calendar.*
import org.codehaus.groovy.runtime.TimeCategory

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(ReportController)
@Mock([Member, InterestTransaction])
class ReportControllerTests {
    def today

    @Before
    void setUp() {
        mockDomain(Member, [
            [id: 1, identificationNumber: "1111111111111", firstname: "Nat", lastname: "Weerawan", telNo: "0891278552", gender: "MALE", address: "11223445"],
            [id: 2, identificationNumber: "2222222222222", firstname: "Noomz", lastname: "Siriwat", telNo: "0811111111", gender: "MALE", address: "2222222"]
        ])

        today = Calendar.instance
        today.set 2012, JANUARY, 1
        today = today.time
    }

    void testDailyInterest() {
        Calendar.metaClass.static.getInstance = {
            today.toCalendar()
        }

        def model = controller.dailyInterest()
        today.set(hourOfDay: 0, minute: 0, second: 0)
        assert model.startDate == today

        use(TimeCategory) {
            assert model.endDate == (today + 24.hours - 1.seconds)
        }
    }
}