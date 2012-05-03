package th.co.opendream.cashcard

import th.co.opendream.cashcard.ActivityType
import th.co.opendream.cashcard.TransferType

import groovy.time.*
import static java.util.Calendar.*
import org.codehaus.groovy.runtime.TimeCategory

class ReportController {

    def sessionUtilService
    def chartService
    def transactionService

    def index() { }

    def balance() {
        def results = Member.findAllByCompanyAndBalanceGreaterThan(sessionUtilService.company, 0.00, [order: 'firstname']).collect {
            [name: "${it.firstname} ${it.lastname}",
             balance: it.balance,
             interest: it.interest,
             memberID: it.id
            ]
        }
        [results: results]
    }

    def dailyInterest() {
        def range = getRange(params)
        def results = InterestTransaction.createCriteria().list(sort: 'date', order: 'asc') {
            between('date', range.startDate, range.endDate)
            member {
                eq('company', sessionUtilService.company)
            }
        }
        [
            interestList: results,
            startDate: range.startDate,
            endDate: range.endDate
        ]
    }

    def dailyTransaction() {
        def range = getRange(params)
        def results = BalanceTransaction.createCriteria().list(sort: 'date', order: 'asc') {
            between('date', range.startDate, range.endDate)
            eq('userCompany', sessionUtilService.company)
        }.collect {
            [
                date: it.date,
                memberID: it.member.id,
                member: it.member,
                code: it.code,
                amount: it.amount,
                credit: it.code == 'PAY' ? it.amount : 0.00,
                debit: it.code == 'WDR' ? it.amount : 0.00
            ]
        }
        def sdebit = 0.00
        def scredit = 0.00
        results.each {
            sdebit += it.debit
            scredit += it.credit
        }
        results << [
            date: null,
            member: '',
            code: 'รวมเงิน',
            credit: scredit,
            debit: sdebit
        ]

        [
            results: results,
            startDate: range.startDate,
            endDate: range.endDate
        ]
    }

    def dailySummary() {
        def range = getRange(params)
        def results = BalanceTransaction.createCriteria().list(sort: 'date', order: 'asc') {
            between('date', range.startDate, range.endDate)
            eq('userCompany', sessionUtilService.company)
        }.collect {
            if (it.activity == ActivityType.WITHDRAW) {
                [
                    date: it.date,
                    memberID: it.member.id,
                    memberIDCard: it.member.identificationNumber,
                    member: it.member,
                    withdraw: it.net,
                    pay: null,
                    interest: null,
                    remainder: null
                ]
            }
            else {
                [
                    date: it.date,
                    memberID: it.member.id,
                    memberIDCard: it.member.identificationNumber,
                    member: it.member,
                    withdraw: null,
                    pay: it.balance_pay,
                    interest: it.interest_pay,
                    remainder: it.remainder
                ]
            }
        }

        def swithdraw = 0.00
        def spay = 0.00
        def sinterest = 0.00
        def sremainder = 0.00
        
        results.each {
            swithdraw  += it.withdraw ? it.withdraw : 0.00
            spay       += it.pay ? it.pay : 0.00
            sinterest  += it.interest ? it.interest : 0.00
            sremainder += it.remainder ? it.remainder : 0.00
        }
        results << [
            date: null,
            memberID: null,
            memberIDCard: null,
            member: null,
            withdraw: swithdraw,
            pay: spay,
            interest: sinterest,
            remainder: sremainder
        ]

        [
            results: results,
            startDate: range.startDate,
            endDate: range.endDate
        ]
    }

    def dailyDiff() {
        def range = getRange(params)
        def results = BalanceTransaction.createCriteria().list {
            between('date', range.startDate, range.endDate)
            eq('userCompany', sessionUtilService.company)
            ne('transferType', TransferType.NONE)
            userCompany {
                order('name')
            }
            order('date')
        }.collect {
            [
                date: it.date,
                member: it.member,
                code: it.code,
                amount: it.amount,
                credit: it.code == 'PAY' ? it.amount : 0.00,
                debit: it.code == 'WDR' ? it.amount : 0.00,
                memberCompany: it.memberCompany
            ]
        }.groupBy { it.memberCompany.name }.entrySet()

        results.each {
            def sdebit = 0.00
            def scredit = 0.00
            it.value.each {
                sdebit += it.debit
                scredit += it.credit
            }
            it.value << [
                date: null,
                member: '',
                code: 'รวมเงิน',
                credit: scredit,
                debit: sdebit
            ]
        }

        println results
        [
            results: results,
            startDate: range.startDate,
            endDate: range.endDate
        ]
    }

    def dailyDiffReceive() {
        def range = getRange(params)
        def results = BalanceTransaction.createCriteria().list {
            between('date', range.startDate, range.endDate)
            eq('memberCompany', sessionUtilService.company)
            eq('transferType', TransferType.RECEIVE)
            memberCompany {
                order('name')
            }
            order('date')
        }.collect {
            [
                date: it.date,
                member: it.member,
                code: it.code,
                amount: it.amount,
                credit: it.code == 'PAY' ? it.amount : 0.00,
                debit: it.code == 'WDR' ? it.amount : 0.00,
                userCompany: it.userCompany
            ]
        }.groupBy { it.userCompany.name }.entrySet()

        results.each {
            def sdebit = 0.00
            def scredit = 0.00
            it.value.each {
                sdebit += it.debit
                scredit += it.credit
            }
            it.value << [
                date: null,
                member: '',
                code: 'รวมเงิน',
                credit: scredit,
                debit: sdebit
            ]
        }

        println results
        [
            results: results,
            startDate: range.startDate,
            endDate: range.endDate
        ]
    }

    def settlement() {
        def range = getRange(params)
        def results = transactionService.settlement(range.startDate, range.endDate)

        def ret = []
        results.entrySet().each {
            def amount = it.value
            if (amount != 0.00) {
                def company = Company.get(it.key)
                ret << [
                    name: company.name,
                    receive: amount < 0.00 ? -1.00 * amount : 0.00,
                    sent: amount > 0.00 ? amount : 0.00
                ]
            }

        }
        [
            results: ret.sort {it.name}
        ]
    }

    def relate() {
        def rows = chartService.relate();
        println rows
        def txt = rows.collect { row ->
            row.collect {
                it.amount
            }.join(',')
        }.collect {
            "[${it}]"
        }.join(',')

        [
            rows: txt
        ]
    }

    def getRange(params) {
        def startDate = params.startDate ?: Calendar.instance.time
        startDate.set(hourOfDay: 0, minute: 0, second: 0)

        def endDate = params.endDate
        if (! endDate) {
            use(TimeCategory) {
                endDate = startDate + 24.hours - 1.seconds
            }
        } else {
            endDate.set(hourOfDay: 23, minute: 59, second: 59)

        }
        return [
            startDate: startDate,
            endDate: endDate
        ]
    }
}