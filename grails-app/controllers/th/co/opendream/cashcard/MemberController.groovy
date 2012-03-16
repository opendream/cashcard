package th.co.opendream.cashcard

import th.co.opendream.cashcard.Member
import th.co.opendream.cashcard.AccountService

class MemberController {
    def utilService
    def transactionService
    def memberService

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
            [
                memberInstance: memberInstance,
                withdrawable: utilService.moneyRoundDown(memberInstance.getTotalDebt()),
                debt: memberInstance.getTotalDebt()
            ]
        }
        else {
            redirect(uri: '/error')
        }
    }

    def pay() {
        def memberInstance = Member.get(params.id)
        params.net = params.amount
        if (memberInstance && params.amount) {

            def withdrawable = utilService.moneyRoundDown(memberInstance.getTotalDebt())
            if (params.amount.toBigDecimal() >  withdrawable) {
                flash.error = message(code: "member.pay.exceed", default: "Can not withdraw with exceed amount")
                render(view: "payment", model: [
                    memberInstance: memberInstance,
                    withdrawable: withdrawable,
                    debt: memberInstance.getTotalDebt()
                    ])
                return
               }
            def change = params.net?.toBigDecimal() - params.amount?.toBigDecimal()
            memberInstance.pay(params.amount.toBigDecimal())
            if (!change) {
                flash.message = message(code: "member.pay.success", args: [params.amount])
            }
            else {
                flash.message = message(code: "member.pay.change", default: "Change is ${change}", args: [change])
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
            def transactionList = c.list(sort: 'date', order: 'desc') {
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

    def edit() {
        def memberInstance = Member.get(params.id)

        if (memberInstance) {
            render(view: 'edit', model: [memberInstance: memberInstance])
        }
        else {
            redirect(uri: '/error')
        }
    }

    def update() {
        def memberInstance = Member.get(params.id)

        if (memberInstance) {
            if (memberInstance.version > params.version.toLong()) {
                memberInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                    [message(code: 'member.label', default: 'Member')] as Object[],
                    "Another user has updated this Member while you were editing")
                render(view: "edit", model: [memberInstance: memberInstance])
                return
            }

            memberInstance.properties = params
            if (!memberService.update(memberInstance)) {
                render(view: "edit", model: [memberInstance: memberInstance])
                return
            }

            flash.message = message(code: "member.update.success", default: "Update success.")
            redirect(action: 'show', id: memberInstance.id)
        }
        else {
            redirect(uri: '/error')
        }
    }

    def disable() {
        def memberInstance = Member.get(params.id)
        if (!memberInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'memberInstance.label', default: 'Member'), params.id])
            redirect(action: "list")
            return
        }

        memberInstance.status = Member.Status.DELETED
        if (memberService.update(memberInstance)) {
            flash.message = message(code: "member.update.success", default: "Update success.")
            redirect(action: "list")
            return
        } else {
            flash.message = message(code: "member.update.failed", default: "Update Failed.")
            memberInstance.errors.rejectValue("status", "member.disable.fail",
                    [message(code: 'member.label', default: 'Member')] as Object[],
                    "Disable failed.")
            render(view: "edit", model: [memberInstance: memberInstance])
            return
        }
    }
}

