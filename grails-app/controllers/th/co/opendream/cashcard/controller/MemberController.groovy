package th.co.opendream.cashcard.controller

import th.co.opendream.cashcard.domain.Member

class MemberController {

    def index() { }

    def create() { 
    	def memberInstance = new Member()
    	render(view:'create', model:[memberInstance:'asdf'])
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
    	
    }
}
