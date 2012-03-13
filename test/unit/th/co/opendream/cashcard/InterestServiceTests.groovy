package th.co.opendream.cashcard

import grails.test.mixin.*
import org.junit.*
import groovy.time.*
import static java.util.Calendar.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(InterestService)
@Mock([Member, Policy])
class InterestServiceTests {

    static transactional = false

    def rate,
        rateLimit,
        dayLeapYear,
        dayNotLeapYear

    @Before
    void setUp() {
        mockDomain(Member, [
            [id: 1, identificationNumber: "1111111111111", firstname: "Nat", lastname: "Weerawan", telNo: "0891278552", gender: "MALE", address: "11223445"]
        ])

        rate = 4.00
        rateLimit = 10000.00

        def day = Calendar.instance

        // Leap year
        day.set 2012, JANUARY, 1
        dayLeapYear = day.time

        // Not Leap year
        day.set 2011, JANUARY, 1
        dayNotLeapYear = day.time
    }

    def generateFindBy(flag) {
        return { key ->
            if (key == Policy.KEY_CREDIT_LINE) {
                return [value: "2000.00"]
            }
            else {
                return [value: flag]
            }
        }
    }

    void testUpdateNonCompound() {
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)

        def m1 = Member.get(1)
        m1.balance = 100.00
        m1.interest = 5.00
        service.update(m1.id, 5.00)

        m1.refresh()
        assert m1.balance == 100.00
        assert m1.interest == 10.00
    }

    void testUpdateCompound() {
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_COMPOUND)

        def m1 = Member.get(1)
        m1.balance = 100.00
        m1.interest = 5.00
        service.update(m1.id, 5.00)

        m1.refresh()
        assert m1.balance == 105.00
        assert m1.interest == 10.00
    }

    void testCalculate() {
        _testCalculate(Policy.VALUE_COMPOUND)
        _testCalculate(Policy.VALUE_NON_COMPOUND)
        _testCalculate(Policy.VALUE_DEFERRED_COMPOUND)
    }

    void _testCalculate(interest_method) {
        Policy.metaClass.static.findByKey = generateFindBy(interest_method)

        def balance = 10000.00

        // Leap year first.
        def tx = service.calculate(dayLeapYear, balance, rate, rateLimit)
        assert tx.interest == 1.09
        assert tx.fee == 0.00

        // Then Not leap year.
        tx = service.calculate(dayNotLeapYear, balance, rate, rateLimit)
        assert tx.interest == 1.10
        assert tx.fee == 0.00
    }
    
    void testUpdateDeferredCompound() {
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_DEFERRED_COMPOUND)

        def m1 = Member.get(1)
        m1.balance = 100.00
        m1.interest = 5.00
        service.update(m1.id, 5.00)

        m1.refresh()
        assert m1.balance == 100.00
        assert m1.interest == 10.00
    }
}