package th.co.opendream.cashcard


class InterestService {
    static transactional = true

    def getRate(date) {
        def result = InterestRate.executeQuery("""
            from InterestRate int 
            where int.startDate = (
                select max(int.startDate) from InterestRate int 
                where int.startDate <= :date
            )
        """, [date: date], [max: 1] )

        result[0].rate
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
