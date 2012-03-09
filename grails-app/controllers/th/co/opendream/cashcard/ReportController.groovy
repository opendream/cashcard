package th.co.opendream.cashcard


import groovy.time.*
import static java.util.Calendar.*
import org.codehaus.groovy.runtime.TimeCategory

class ReportController {

    def index() { }

    def dailyInterest() {
        def range = getRange(params)
        def results = InterestTransaction.createCriteria().list {
            between('date', range.startDate, range.endDate)
            member {
                order('firstname')
                order('lastname')
            }
        }
        [
            interestList: results,
            startDate: range.startDate,
            endDate: range.endDate
        ]
    }

    def dailyTransaction() {
        def range = getRange(params)
        def results = BalanceTransaction.createCriteria().list {
            between('date', range.startDate, range.endDate)
            order('date')
            member {
                order('firstname')
                order('lastname')
            }
        }
        [
            results: results,
            startDate: range.startDate,
            endDate: range.endDate
        ]
    }

    def getRange(params) {
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
        return [
            startDate: startDate,
            endDate: endDate
        ]
    }
}