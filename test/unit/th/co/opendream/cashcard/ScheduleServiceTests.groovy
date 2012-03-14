package th.co.opendream.cashcard

import grails.test.mixin.*
import org.junit.*
import groovy.time.*
import static java.util.Calendar.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ScheduleService)
@Mock([Member, Policy, InterestService, AccountService, InterestRate])
class ScheduleServiceTests {

    void testSomething() {

    }
    
}