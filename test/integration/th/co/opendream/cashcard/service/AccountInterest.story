package th.co.opendream.cashcard.service

import th.co.opendream.cashcard.Member
import th.co.opendream.cashcard.Policy
import java.text.SimpleDateFormat


description "calculate an interest and accumulate it"

before "load interest service", {
    inject "interestService"
    
    df = new SimpleDateFormat("yyyy-MM-dd", Locale.US)
    
    prepareInterestBalance = { accountId, amount -> 
        new Member(
            identificationNumber: accountId,
            firstname: 'first',
            lastname: 'last',
            gender: Member.Gender.MALE,
            address: '123 silom streest, bkk',
            telNo: '021234567',
            balance: 100.00,
            interest: amount
        ).save()
    }
}

scenario "Calculate an interest", {
    given "Account balance is equal 200.00", {
        balance = 200.00
    }
    and "Interest rate is equal 18.00%", {
        rate = 18.00
    }
    and "date is 2011-02-11", {
        date = df.parse('2011-02-11')
    }

    when "request for an interest", {
        tx = interestService.calculate(date, balance, rate, 18.00)
    }

    then "interest should be 0.10", {
        tx.interest.shouldBe 0.10
    }
}

scenario "Each account Interest and balance should accumulate separately", {
    given "Current interest balance of account 'A' is equals 17.30", {
        prepareInterestBalance('1234567890123', 17.30)
    }
    and "account balance is 100.00"
    and "interest compound method is used", {
        Policy.metaClass.static.findByKey = { key -> [value: Policy.VALUE_COMPOUND] }
    }

    when "an interest value of 0.73 is calculated and added to account 'A'", {
        id = Member.findByIdentificationNumber('1234567890123').id
        member = interestService.update(id, 0.73)
    }

    then "a new accumulated interest of account 'A' should be 18.03", {
        member.interest.shouldBe 18.03
    }
    and "new balance should be 100.73", {
        member.balance.shouldBe 100.73
    }

    given "account 'B' has no interest balance", {
        prepareInterestBalance('1234567890124', 0.00)
    }
    and "account balance is 100.00"
    and "interest compound method is used", {
        Policy.metaClass.static.findByKey = { key -> [value: Policy.VALUE_NON_COMPOUND] }
    }
    
    when "an interest value of 0.15 is calculated and added to account 'B'", {
        id = Member.findByIdentificationNumber('1234567890124').id
        member = interestService.update(id, 0.15)
    }

    then "a new accumulated interest of account 'B' should be 0.15", {
        member.interest.shouldBe 0.15
    }
    and "new balance should be 100.00", {
        member.balance.shouldBe 100.00
    }
}

scenario "Calculate interest and fee with rate over max interest rate, not in a leap year", {
    given "interest rate is 24.00", {
        interestRate = 24.00
    }
    and "interest rate limit is 18.00", {
        rateLimit = 18.00
    }
    when "account balance has 1234567.00", {
        balance = 1234567.00
    }
    and "calculate interest and fee", {
        interestTx = interestService.calculate(
                        df.parse('2011-02-11'), balance, interestRate, rateLimit)
    }
    then "interest should be 608.83", {
        interestTx.interest.shouldBe 608.83
    }
    and "fee should be 202.94", {
        interestTx.fee.shouldBe 202.94
    }
}

scenario "Calculate interest and fee with rate not exceed max interest rate, not in a leap year", {
    given "interest rate is 24.00", {
        interestRate = 17.00
    }
    and "interest rate limit is 18.00", {
        rateLimit = 18.00
    }
    when "account balance has 1234567.00", {
        balance = 1234567.00
    }
    and "calculate interest and fee", {
        interestTx = interestService.calculate(
                        df.parse('2011-02-11'), balance, interestRate, rateLimit)
    }
    then "interest should be 575.00", {
        interestTx.interest.shouldBe 575.00
    }
    and "fee should be 0.00", {
        interestTx.fee.shouldBe 0.00
    }
}

scenario "Calculate interest and fee with rate over max interest rate, in a leap year", {
    given "interest rate is 24.00", {
        interestRate = 24.00
    }
    and "interest rate limit is 18.00", {
        rateLimit = 18.00
    }
    when "account balance has 1234567.00", {
        balance = 1234567.00
    }
    and "calculate interest and fee", {
        interestTx = interestService.calculate(
                        df.parse('2012-02-11'), balance, interestRate, rateLimit)
    }
    then "interest should be 607.16", {
        interestTx.interest.shouldBe 607.16
    }
    and "fee should be 202.39", {
        interestTx.fee.shouldBe 202.39
    }
}

scenario "Calculate interest and fee with rate not exceed max interest rate, in a leap year", {
    given "interest rate is 24.00", {
        interestRate = 17.00
    }
    and "interest rate limit is 18.00", {
        rateLimit = 18.00
    }
    when "account balance has 1234567.00", {
        balance = 1234567.00
    }
    and "calculate interest and fee", {
        interestTx = interestService.calculate(
                        df.parse('2012-02-11'), balance, interestRate, rateLimit)
    }
    then "interest should be 573.43", {
        interestTx.interest.shouldBe 573.43
    }
    and "fee should be 0.00", {
        interestTx.fee.shouldBe 0.00
    }
}