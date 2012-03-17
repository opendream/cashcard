package th.co.opendream.cashcard


import th.co.opendream.cashcard.TransferType

import groovy.time.*
import static java.util.Calendar.*
import org.codehaus.groovy.runtime.TimeCategory

class ReportController {

    def sessionUtilService

    def index() { }

    def dailyInterest() {
        def range = getRange(params)
        def results = InterestTransaction.createCriteria().list {
            between('date', range.startDate, range.endDate)
            member {
                order('firstname')
                order('lastname')
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
        def results = BalanceTransaction.createCriteria().list {
            between('date', range.startDate, range.endDate)
            eq('userCompany', sessionUtilService.company)
            order('date')
            member {
                order('firstname')
                order('lastname')
            }
        }.collect {
            [
                date: it.date,
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

    def dailyDiff() {
        def range = getRange(params)
        def results = BalanceTransaction.createCriteria().list {
            between('date', range.startDate, range.endDate)
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