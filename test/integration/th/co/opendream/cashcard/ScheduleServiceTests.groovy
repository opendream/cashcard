package th.co.opendream.cashcard

import grails.test.mixin.*
import org.junit.*
import groovy.time.*
import static java.util.Calendar.*

class ScheduleServiceTests {

    static transactional = false

    def m1, m2


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

        m1 = new Member(identificationNumber:"1159900100015", firstname:"Nat", lastname: "Weerawan", telNo: "111111111", gender: "MALE", address: "Opendream")
        m2 = new Member(identificationNumber: "1234567891234", firstname: "Noomz", lastname: "Siriwat", telNo: "111111111", gender: "MALE", address: "Opendream2")

        m1.save()
        m2.save()

        new Policy(key: Policy.KEY_CREDIT_LINE, value: 2000000000).save() // 2 billion baht limit
        new Policy(key: Policy.KEY_INTEREST_METHOD, value: Policy.VALUE_NON_COMPOUND).save()
        new Policy(key: Policy.KEY_INTEREST_RATE_LIMIT, value: '18.00').save()

        m1 = Member.findByFirstname("Nat")
        m2 = Member.findByFirstname("Noomz")
    }

    @After
    void tearDown() {
        Member.list().each {
            it.delete()
        }

        Transaction.list().each {
            it.delete()
        }

    }
    void testScheduleDeferredCompound() {
        def count = InterestTransaction.count()

        new InterestRate(startDate: dayLeapYear, rate: 4.00).save()

        def interest_policy = Policy.findByKey(Policy.KEY_INTEREST_METHOD)
        interest_policy.value = Policy.VALUE_DEFERRED_COMPOUND
        interest_policy.save()

        m1.balance = 1000.00
        m1.save()

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