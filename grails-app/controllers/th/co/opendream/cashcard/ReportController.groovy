package th.co.opendream.cashcard


import groovy.time.*
import static java.util.Calendar.*


class ReportController {

    def index() { }

    def dailyInterest() {
        def today = Calendar.instance.time
        today.set(hourOfDay: 0, minute: 0, second:0)

        [interestList: InterestTransaction.findAllByDateBetween(today, today.plus(1))]
      }  
}
   