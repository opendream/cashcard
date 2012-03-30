package th.co.opendream.cashcard

import grails.converters.JSON

class ApiController {
    def apiService

    def index() { }

    def getMemberTransactionHistory() {
        def member = Member.get(params.memberId as Integer)
        def transactionHistory = []

        if (member) {
            transactionHistory = apiService.getMemberTransactionHistory(member)
        }

        render transactionHistory as JSON
    }
}