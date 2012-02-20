package th.co.opendream.cashcard.service

description "calculate an interest and accumulate it"

before "load interest service", {
    inject "interestService"
    
    prepareInterestBalance = { accountId, amount -> 

    }
}

scenario "Calculate an interest", {
    given "Account balance is equal 200.00", {
        balance = 200.00
    }
    and "Interest rate is equal 18.00%", {
        rate = 18.00
    }

    when "request for an interest", {
        interest = interestService.calculate(balance, rate)
    }

    then "interest should be 0.10", {
        interest.shouldBe 0.10
    }
}

scenario "Each account Interest balance should accumulate separately", {
    given "Current interest balance of account 'A' is equals 17.30", {
       prepareInterestBalance 'A', 17.30
    }

    when "an interest value of 0.73 is calculated and added to account 'A'", {
        interest = interestService.update('A', 0.73)
    }

    then "a new accumulated interest of account 'A' should be 18.03", {
        interest.shouldBe 18.03
    }

    given "account 'B' has no interest balance", {
        prepareInterestBalance 'B', 0.00
    }

    when "an interest value of 0.15 is calculated and added to account 'B'", {
        interest = interestService.update('B', 0.15)
    }

    then "a new accumulated interest of account 'B' should be 0.15", {
        interest.shouldBe 0.15
    }
}