package th.co.opendream.cashcard

import org.springframework.dao.DataIntegrityViolationException

class MemberHistoryController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [memberHistoryInstanceList: MemberHistory.list(params), memberHistoryInstanceTotal: MemberHistory.count()]
    }

    def create() {
        [memberHistoryInstance: new MemberHistory(params)]
    }

    def save() {
        def memberHistoryInstance = new MemberHistory(params)
        if (!memberHistoryInstance.save(flush: true)) {
            render(view: "create", model: [memberHistoryInstance: memberHistoryInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'memberHistory.label', default: 'MemberHistory'), memberHistoryInstance.id])
        redirect(action: "show", id: memberHistoryInstance.id)
    }

    def show() {
        def memberHistoryInstance = MemberHistory.get(params.id)
        if (!memberHistoryInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'memberHistory.label', default: 'MemberHistory'), params.id])
            redirect(action: "list")
            return
        }

        [memberHistoryInstance: memberHistoryInstance]
    }

    def edit() {
        def memberHistoryInstance = MemberHistory.get(params.id)
        if (!memberHistoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'memberHistory.label', default: 'MemberHistory'), params.id])
            redirect(action: "list")
            return
        }

        [memberHistoryInstance: memberHistoryInstance]
    }

    def update() {
        def memberHistoryInstance = MemberHistory.get(params.id)
        if (!memberHistoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'memberHistory.label', default: 'MemberHistory'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (memberHistoryInstance.version > version) {
                memberHistoryInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'memberHistory.label', default: 'MemberHistory')] as Object[],
                          "Another user has updated this MemberHistory while you were editing")
                render(view: "edit", model: [memberHistoryInstance: memberHistoryInstance])
                return
            }
        }

        memberHistoryInstance.properties = params

        if (!memberHistoryInstance.save(flush: true)) {
            render(view: "edit", model: [memberHistoryInstance: memberHistoryInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'memberHistory.label', default: 'MemberHistory'), memberHistoryInstance.id])
        redirect(action: "show", id: memberHistoryInstance.id)
    }

    def delete() {
        def memberHistoryInstance = MemberHistory.get(params.id)
        if (!memberHistoryInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'memberHistory.label', default: 'MemberHistory'), params.id])
            redirect(action: "list")
            return
        }

        try {
            memberHistoryInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'memberHistory.label', default: 'MemberHistory'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'memberHistory.label', default: 'MemberHistory'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
