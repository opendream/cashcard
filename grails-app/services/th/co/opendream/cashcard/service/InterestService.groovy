package th.co.opendream.cashcard.service


class InterestService {
    static transactional = true

    def getRate(date) {
        0.00
    }
 
    def getBalanceList() {
        [
            [accountId: 'A', balance: 0.00], 
            [accountId: 'B', balance: 0.00]
        ]
    }
    
    def calculate(balance, rate) {
        0.00
    }
    
    def update(accountId, interest) {
        0.00
    }
    
}
