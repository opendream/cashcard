package th.co.opendream.cashcard


import groovy.time.*
import static java.util.Calendar.*
import org.codehaus.groovy.runtime.TimeCategory

class ReportController {

    def index() { }

    def dailyInterest() {
        def startDate = params.startDate ?: Calendar.instance.time
        startDate.set(hourOfDay: 0, minute: 0, second: 0)

        def endDate = params.endDate
        if (! endDate) {
            use(TimeCategory) {
                endDate = startDate + 24.hours - 1.seconds
            }
        } else {
            endDate.set(hourOfDay: 23, minute: 59, second: 59)

        }
        def results = InterestTransaction.findAllByDateBetween(
            startDate,
            endDate)

        [
            interestList: results,
            startDate: startDate,
            endDate: endDate
        ]
    }
}