package th.co.opendream.cashcard.service

import java.text.SimpleDateFormat


description "Request an interest rate from interest service"

before "prepare helper functions", {
    inject "interestService"
    def df = new SimpleDateFormat("yyyy-MM-dd", Locale.US)

    prepareRate = { params->
        def from = date(params.from)
        def to = date(params.to)
        
        new th.co.opendream.cashcard.domain.InterestRate(
            startDate: from, endDate: to, rate: params.rate).save(flush: true)
    }

    date = { str ->
        df.parse(str)
    }
}

given "rate between 2011-01-01 and 2011-11-01 is 5%", {
    prepareRate(
        from: '2011-01-01',
        to: '2011-11-01',
        rate: 5.00
    )
}

given "rate between 2011-11-02 and 2011-12-31 is 7%", {
    prepareRate(
        from: '2011-01-02',
        to: '2011-12-31',
        rate: 7.00
    )
}

scenario "Request interest rate with valid date", {
    when "request for an interest rate on '2011-01-01'", {
        rate = interestService.getRate(date('2011-01-01'))
    }

    then "rate should be 5%", {
        rate.shouldBe(5.00)
    }

    when "request for an interest rate on '2011-12-20'", {
        rate = interestService.getRate(date('2011-12-20'))
    }

    then "rate should be 7%", {
        rate.shouldBe(7.00)
    }
}

scenario "Request interest rate with invalid date", {
    when "request for an interest rate on '2001-01-01'", {
        getRate = {
            interestService.getRate(date('2001-01-01'))
            throw new RuntimeException('test')
        }
    }

    then "service should throw an exception", {
        ensureThrows(RuntimeException){
            getRate()
        }
    }
}