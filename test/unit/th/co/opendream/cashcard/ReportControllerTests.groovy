package th.co.opendream.cashcard

import grails.test.mixin.*
import org.junit.*
import groovy.time.*
import static java.util.Calendar.*
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
        def m1 = Member.get(1)
        m1.balance = 100.00
        m1.interest = 10.00
        m1.save()
        def m2 = Member.get(2)
        m2.balance = 200.00
        m2.interest = 21.00
        m2.save()

        mockDomain(InterestTransaction, [
            [id: 1, member: m1, amount: 10.00, txType: TransactionType.CREDIT, date: today, fee: 0.00, interest: 10.00],
            [id: 2, member: m2, amount: 21.00, txType: TransactionType.CREDIT, date: today, fee: 0.00, interest: 21.00],
            [id: 3, member: m1, amount: 13.00, txType: TransactionType.CREDIT, date: today.plus(1), fee: 0.00, interest: 13.00],
            [id: 4, member: m2, amount: 24.00, txType: TransactionType.CREDIT, date: today.plus(1), fee: 0.00, interest: 24.00],
        ])
        ///////assert InterestTransaction.get(1).date == 2
        def interestList

        Calendar.metaClass.static.getInstance = {
            today.toCalendar()
        }

        interestList = controller.dailyInterest().interestList
        assert interestList != null

        def row1 = interestList.find {
            it.id == 1
        }
        assertNotNull row1
        assert row1.member.id == 1

        def row2 = interestList.find {
            it.id == 2
        }
        assertNotNull row2
        assert row2.member.id == 2

        response.reset()

        Calendar.metaClass.static.getInstance = {
            today.plus(1).toCalendar()
        }
        interestList = controller.dailyInterest().interestList
        assertNotNull interestList

        row1 = interestList.find {
            it.id == 3
        }
        assertNotNull row1
        assert row1.member.id == 1

        row2 = interestList.find {
            it.id == 4
        }
        assertNotNull row2
        assert row2.member.id == 2
    }
}