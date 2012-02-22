package th.co.opendream.cashcard.controller

import org.springframework.dao.DataIntegrityViolationException
import th.co.opendream.cashcard.domain.InterestRate

class InterestRateController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.sort = "startDate"
        [interestRateInstanceList: InterestRate.list(params), interestRateInstanceTotal: InterestRate.count()]
    }

    def create() {
        [interestRateInstance: new InterestRate(params)]
    }

    def save() {
        def interestRateInstance = new InterestRate(params)
        if (!interestRateInstance.save(flush: true)) {
            render(view: "create", model: [interestRateInstance: interestRateInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'interestRate.label', default: 'InterestRate'), interestRateInstance.id])
        redirect action: "list"
    }

    def edit() {
        def interestRateInstance = InterestRate.get(params.id)
        if (!interestRateInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'interestRate.label', default: 'InterestRate'), params.id])
            redirect(action: "list")
            return
        }

        [interestRateInstance: interestRateInstance]
    }

    def update() {
        def interestRateInstance = InterestRate.get(params.id)
        if (!interestRateInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'interestRate.label', default: 'InterestRate'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (interestRateInstance.version > version) {
                interestRateInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'interestRate.label', default: 'InterestRate')] as Object[],
                          "Another user has updated this InterestRate while you were editing")
                render(view: "edit", model: [interestRateInstance: interestRateInstance])
                return
            }
        }

        interestRateInstance.properties = params

        if (!interestRateInstance.save(flush: true)) {
            render(view: "edit", model: [interestRateInstance: interestRateInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'interestRate.label', default: 'InterestRate'), interestRateInstance.id])
        redirect(action: "list")
    }

    def delete() {
        def interestRateInstance = InterestRate.get(params.id)
        if (!interestRateInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'interestRate.label', default: 'InterestRate'), params.id])
            redirect(action: "list")
            return
        }

        try {
            interestRateInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'interestRate.label', default: 'InterestRate'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'interestRate.label', default: 'InterestRate'), params.id])
            redirect(action: "list")
        }
    }
}
