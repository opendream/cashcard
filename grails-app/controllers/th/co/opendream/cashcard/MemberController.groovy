package th.co.opendream.cashcard

import th.co.opendream.cashcard.Member
import th.co.opendream.cashcard.AccountService

class MemberController {
    def utilService
    
    def index() { }

    def create() {
        def memberInstance = new Member()
        render(view:'create', model:[memberInstance: memberInstance])
    }

    def save() {
        def memberInstance = new Member(params)
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
            render(view:'show', model:[memberInstance: memberInstance])
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

        if (memberInstance) {
            if (!params.amount) {
                render(view: 'withdraw', model: [memberInstance: memberInstance])
            }
            else if (memberInstance.canWithdraw(params.amount)) {
                memberInstance.withdraw(params.amount)
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

            [memberInstance: memberInstance, totalDebt: utilService.moneyRoundUp(memberInstance.getTotalDebt())]
        }
        else {
            redirect(uri: '/error')
        }
    }

    def pay() {
        def memberInstance = Member.get(params.id)
        if (memberInstance && params.amount) {
            def change = memberInstance.pay(params.amount)
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
            def transactionList = c.list(offset: params.offset, max: params.max) {
                member {
                    eq('id', memberInstance.id)
                }
            }

            render(view: 'transaction', model:[transactionList: transactionList, memberInstance: memberInstance, transactionCount: transactionList.totalCount])
        }
        else {
            redirect(uri: '/error')
        }
    }

}
        