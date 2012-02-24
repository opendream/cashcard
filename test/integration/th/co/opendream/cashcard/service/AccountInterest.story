package th.co.opendream.cashcard.service

import th.co.opendream.cashcard.Member
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
        interest = interestService.calculate(date, balance, rate)
    }

    then "interest should be 0.10", {
        interest.shouldBe 0.10
    }
}

scenario "Each account Interest balance should accumulate separately", {
    given "Current interest balance of account 'A' is equals 17.30", {
        prepareInterestBalance('1234567890123', 17.30)
    }

    when "an interest value of 0.73 is calculated and added to account 'A'", {
        id = Member.findByIdentificationNumber('1234567890123').id
        interest = interestService.update(id, 0.73)
    }

    then "a new accumulated interest of account 'A' should be 18.03", {
        interest.shouldBe 18.03
    }

    given "account 'B' has no interest balance", {
        prepareInterestBalance('1234567890124', 0.00)
    }

    when "an interest value of 0.15 is calculated and added to account 'B'", {
        id = Member.findByIdentificationNumber('1234567890124').id
        interest = interestService.update(id, 0.15)
    }

    then "a new accumulated interest of account 'B' should be 0.15", {
        interest.shouldBe 0.15
    }
}