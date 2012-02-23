package th.co.opendream.cashcard

import th.co.opendream.cashcard.*

class InterestService {
    static transactional = true

    def getRate(date) {
    println InterestRate.list().collect { it.startDate
    }

    println date

        def c = InterestRate.createCriteria()
        def result = c.get {
            ge 'startDate', date
            le 'endDate', date
        }

        result.rate
    }

    def calculate(date, balance, rate) {
        def cal = new GregorianCalendar()
        def isLeap = cal.isLeapYear(date.year + 1900)
        def yearDivider = isLeap ? 366 : 365

        (rate * balance / 100.00) / yearDivider
    }

    def update(accountId, interest) {
        def member = Member.get(accountId)
        member.interest += interest

        member.save()
        member.interest
    }

}
