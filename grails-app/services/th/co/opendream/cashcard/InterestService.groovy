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

        def unscaled = (rate * balance / 100.00) / yearDivider
        
        // TODO... do we need policy on rounding interest decimal digit? 
        // such as rounding -> up/down/half-up/half-down
        // always return decimal with 2 digits
        unscaled.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    def update(accountId, interest) {
        def member = Member.get(accountId)
        member.interest += interest

        member.save()
        member.interest
    }

}
