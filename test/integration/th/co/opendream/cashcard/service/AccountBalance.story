package th.co.opendream.cashcard.service

scenario "Request account balance list from interest service", {
    before "prepare helper functions", {
        inject "interestService"
        
        prepareBalance = {accountId, balance ->

        }
    }
    given "A current balance of account 'A' is 1200.00", {
        prepareBalance 'A', 1200.00
    }
    and "A current balance of account 'B' is 120.00", {
        prepareBalance 'B', 120.00
    }

    when "request for a balance list", {
        balances = interestService.getBalanceList()
    }

    then "service shoud return 2 records", {
        balances.size().shouldBe(2)
    }
    and "balance of account 'A' should be 1200.00", {
        balances.find{it.accountId == 'A'}.balance.shouldBe(1200.00)
    }
    and "balance of account 'B' should be 120.00", {
        balances.find{it.accountId == 'B'}.balance.shouldBe(120.00)
    }
}
