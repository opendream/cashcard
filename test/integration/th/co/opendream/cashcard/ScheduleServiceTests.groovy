package th.co.opendream.cashcard

import grails.test.mixin.*
import org.junit.*
import groovy.time.*
import static java.util.Calendar.*

class ScheduleServiceTests {

    static transactional = false

    // Services
    def scheduleService

    def rate,
        rateLimit,
        dayLeapYear,
        dayNotLeapYear

    @Before
    void setUp() {
        def day = Calendar.instance

        // Leap year
        day.set 2012, JANUARY, 1
        dayLeapYear = day.time

        // Not Leap year
        day.set 2011, JANUARY, 1
        dayNotLeapYear = day.time
    }

    void testScheduleDeferredCompound() {
        def count = InterestTransaction.count()

        new InterestRate(startDate: dayLeapYear, rate: 4.00).save()

        def interest_policy = Policy.findByKey(Policy.KEY_INTEREST_METHOD)
        interest_policy.value = Policy.VALUE_DEFERRED_COMPOUND
        interest_policy.save()

        def m1 = Member.get(1)
        def m2 = Member.get(2)

        m1.balance = 1000.00
        m1.save()
        println m1.errors

        m2.balance = 2000.00
        m2.save()

        scheduleService.updateInterest()

        m1.refresh()
        m2.refresh()

        assert m1.balance == 1000.00
        assert m1.interest == 0.11
        assert m2.balance == 2000.00
        assert m2.interest == 0.22
        assert InterestTransaction.count() == count + 2
    }

}