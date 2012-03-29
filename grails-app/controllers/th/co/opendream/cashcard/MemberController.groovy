package th.co.opendream.cashcard

import th.co.opendream.cashcard.Member
import th.co.opendream.cashcard.AccountService
import th.co.opendream.cashcard.TransactionType
import groovy.json.JsonSlurper

class MemberController {
    def utilService
    def transactionService
    def sessionUtilService

    def index() { }

    def create() {
        def memberInstance = new Member()
        render(view:'create', model:[memberInstance: memberInstance])
    }

    def save() {
        def memberInstance = new Member(params)
        memberInstance.company = sessionUtilService.company
        memberInstance.interestMethod = Policy.valueOf(Policy.KEY_INTEREST_METHOD)

        if ( memberInstance.save() ) {
          redirect(action: "show", id: memberInstance.id)
        }
        else {
          render(view:'create', model:[memberInstance: memberInstance])
        }
    }

    def show() {
        if (params.id) {
            def memberInstance = Member.get(params.id)
            def isOrigCompany = memberInstance.company != sessionUtilService.company
            render(view:'show', model:[memberInstance: memberInstance, isOrigCompany: isOrigCompany])
        }
        else {
            redirect(uri: '/error')
        }
    }

    def list() {
        params.offset = params.offset ? params.int('offset') : 0
        params.max = params.max ? params.int('max') : 10

        def c = Member.createCriteria()
        def memberList = c.list(offset: params.offset, max: params.max) {
            if (params.identificationNumber) {
                eq('identificationNumber', params.identificationNumber)
            }
            if (params.firstname) {
                ilike('firstname', '%' + params.firstname + '%')
            }
            if (params.lastname) {
                ilike('lastname', '%' + params.lastname + '%')
            }
            if (params.telNo) {
                ilike('telNo' , '%' + params.telNo + '%')
            }
            eq('company', sessionUtilService.company)
        }

        render (view: 'list', model:[memberList: memberList, memberCount: memberList.totalCount])
    }

    def verifyCard(String cardId) {
		flash.error = null // Clear flash

        def memberInstance = Member.findByIdentificationNumber("${cardId}")
        if (memberInstance) {
            redirect(action: "show", id: memberInstance.id)
        }
        else if (cardId != null && cardId != '') {
            flash.error = "${cardId} not found"
            render(view: 'verifyCard')
        }
        else {
            render(view: 'verifyCard')
        }
    }

    def withdraw() {
        def memberInstance = Member.get(params.id)
        flash.error = null
        def amount = params.amount?.replace(',', '')?.toBigDecimal()
        if (memberInstance) {
            if (!params.amount) {
                render(view: 'withdraw', model: [memberInstance: memberInstance])
            }
            else if (memberInstance.canWithdraw(amount)) {
                memberInstance.withdraw(amount)
                redirect(action: "show", id: memberInstance.id)
            }
            else {
                flash.error = "Can't withdraw. Invalid amount"
                render(view: 'withdraw', model: [memberInstance: memberInstance])
            }
        }
        else {
            redirect(uri: '/error')
        }
    }

    def payment() {
        def memberInstance = Member.get(params.id)

        if (memberInstance) {
            //memberInstance.metaClass.getTotalDebt = { utilService.moneyRoundUp(memberInstance.getTotalDebt()) }

            [memberInstance: memberInstance,
            roundUpDebt: utilService.moneyRoundUp(memberInstance.getTotalDebt()),
            debt: memberInstance.getTotalDebt()
            ]
        }
        else {
            redirect(uri: '/error')
        }
    }

    def pay() {
        def memberInstance = Member.get(params.id)
        if (memberInstance && params.amount) {
            memberInstance.pay(params.amount.toBigDecimal())
            def change = params.net?.toBigDecimal() - params.amount?.toBigDecimal()


            if (!change) {
                flash.message = "Pay success."
            }
            else {
                flash.message = "Change is ${change}"
            }
            redirect(action: "show", id: memberInstance.id)
        }
        else if (!params.amount) {
            flash.message = "Invalid amount."
            redirect(action: "payment", id: memberInstance.id)
        }
        else {
            redirect(uri: '/error')
        }
    }

    def transaction() {
        def memberInstance = Member.get(params.id)

        if (memberInstance) {
            params.offset = params.offset ? params.int('offset') : 0
            params.max = params.max ? params.int('max') : 10

            def c = BalanceTransaction.createCriteria()
            def transactionList = c.list(sort: 'date', order: 'asc') {
                member {
                    eq('id', memberInstance.id)
                }
            }
            def totalCount = transactionList.totalCount
            transactionList = transactionList.collect {
                [
                    date: it.date,
                    activity: it.activity,
                    amount: it.amount,
                    debit: (it.txType == TransactionType.CREDIT) ? it.amount : 0.00,
                    credit: (it.txType == TransactionType.DEBIT) ? it.amount : 0.00,
                    balance: it.balance,
                    remark: (it.userCompany != sessionUtilService.company ? it.userCompany.name : ''),
                ]
            }
            render(view: 'transaction', model:[transactionList: transactionList, memberInstance: memberInstance, transactionCount: totalCount])
        }
        else {
            redirect(uri: '/error')
        }
    }

    def transactionAll() {
        def memberInstance = Member.get(params.id)

        if (memberInstance) {
            def link = createLink(controller: 'api', action: 'getTransactionHistory', params: [memberId: memberInstance.id], absolute: true) as String
            println link
            def payload = new URL(link).text
            println payload

            def slurper = new JsonSlurper()
            def doc = slurper.parseText(payload)

            println(doc)
            def totalCount = transactionList.totalCount
            transactionList = transactionList.collect {
                [
                    date: it.date,
                    activity: it.activity,
                    amount: it.amount,
                    debit: (it.txType == TransactionType.CREDIT) ? it.amount : 0.00,
                    credit: (it.txType == TransactionType.DEBIT) ? it.amount : 0.00,
                    balance: it.balance,
                    remark: (it.userCompany != sessionUtilService.company ? it.userCompany.name : ''),
                ]
            }
            render(view: 'transaction', model:[transactionList: transactionList, memberInstance: memberInstance, transactionCount: totalCount])
        }
        else {
            redirect(uri: '/error')
        }
    }
}

