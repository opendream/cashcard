package th.co.opendream.cashcard.controller

import th.co.opendream.cashcard.domain.Member

class MemberController {

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
        def memberInstance = Member.findAllByIdentificationNumber(cardId)
        if (memberInstance) {
            redirect(action: "show", id: memberInstance.id)
        }
        else if (cardId != null) {
            flash.error = "${cardId} not found"
            render(view: 'verifyCard')
        }
        else {
            render(view: 'verifyCard')
        }
    }

}
