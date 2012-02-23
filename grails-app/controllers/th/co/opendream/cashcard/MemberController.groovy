package th.co.opendream.cashcard

import th.co.opendream.cashcard.Member
import th.co.opendream.cashcard.AccountService

class MemberController {
    def accountService

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
        def memberList = Member.list()
        render (view: 'list', model:[memberList: memberList])
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

        def filterUid = { it? it : 0 }
        def uid = filterUid(params.uid)
        def memberInstance = Member.get(uid)
        flash.error = null

        if (memberInstance) {
            if (!params.amount) {
                render(view: 'withdraw', model: [memberInstance: memberInstance])
            }
            else if (accountService.canWithdraw(memberInstance, params.amount)) {
                accountService.withdraw(memberInstance, params.amount)
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

}
