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

    def calculate(date, balance, rate, rateLimit) {
        def fee = 0.00
        def feeRate = 0.00
            
        if (rate > rateLimit) {
            feeRate = rate - rateLimit
        }
        
        def cal = new GregorianCalendar()
        def isLeap = cal.isLeapYear(date.year + 1900)
        def yearDivider = isLeap ? 366 : 365

        def interest = (rate * balance / 100.00) / yearDivider
        
        // always return decimal with 2 digits
        interest = interest.setScale(2, BigDecimal.ROUND_HALF_UP)
        
        if (feeRate > 0.00) {
            fee = (interest * feeRate) / rate
            fee = fee.setScale(2, BigDecimal.ROUND_HALF_UP)
        }
        
        interest -= fee
        
        new InterestTransaction(interest: interest, fee: fee)
    }

    def update(accountId, interest) {
        def member = Member.get(accountId)
        member.interest += interest

        if (Policy.isCompoundMethod()) {
            member.balance += interest
        }
        member.save()
        member
    }

}
