package th.co.opendream.cashcard

import th.co.opendream.cashcard.Member
import th.co.opendream.cashcard.AccountService
import th.co.opendream.cashcard.TransactionType

class MemberController {
    def utilService
    def transactionService
    def sessionUtilService
    def memberService

    def index() { }

    def create() {
        def memberInstance = new Member()
        render(view:'create', model:[memberInstance: memberInstance])
    }

    def save() {
        def memberInstance = new Member(params)
        memberInstance.company = sessionUtilService.company

        if ( memberInstance.save() ) {
          flash.message = "ลงทะเบียนสมาชิกเรียบร้อยแล้ว"
          redirect(action: "show", id: memberInstance.id)
        }
        else {
          println memberInstance.errors
          render(view:'create', model:[memberInstance: memberInstance])
        }
    }

    def show() {
        if (params.id) {
            def memberInstance = Member.get(params.id)
            if (memberInstance) {
                def isOrigCompany = memberInstance.company != sessionUtilService.company
                render(view:'show', model:[memberInstance: memberInstance, isOrigCompany: isOrigCompany])
            }
            else {
                redirect(uri: '/error')
            }
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
            flash.error = "ไม่พบสมาชิกที่มีหมายเลขบัตรประชาชน ${cardId}, ต้องการลงทะเบียนสมาชิกใหม่? โปรดไปที่ " + link(controller: "member", action: "create") { "ลงทะเบียน" }
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
                flash.message = "กู้เงินจำนวน ${amount} บาท เรียบร้อย"
                redirect(action: "show", id: memberInstance.id)
            }
            else {
                flash.error = "ไม่สามารถให้กู้เงินได้ เนื่องจากเกินวงเกินกู้"
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
            def change = params.net?.toBigDecimal() - params.amount?.toBigDecimal()

            if (change < 0.00) {
                flash.error = "ไม่สามารถทำรายการได้ กรุณาตรวจสอบจำนวนเงิน"
                render (action: 'payment', id: memberInstance)
                render(view: 'payment', model:
                    [
                        memberInstance: memberInstance,
                        roundUpDebt: utilService.moneyRoundUp(memberInstance.getTotalDebt()),
                        debt: memberInstance.getTotalDebt(),
                        net: params.net,
                        amount: params.amount
                    ])
            }
            else {
                memberInstance.pay(params.amount.toBigDecimal())
                if (!change) {
                    flash.message = "รับชำระเงินเรียบร้อย"
                }
                else {
                    flash.message = "ต้องทอนเงินจำนวน ${change} บาท"
                }
                redirect(action: "show", id: memberInstance.id)
            }
        }
        else if (!params.amount) {
            flash.error = "จำนวนเงินไม่ถูกต้อง"
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
            println transactionList
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
            flash.message = "จำหน่ายสมาชิกเรียบร้อย"
            redirect(action: "show", id: memberInstance.id)
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

    def enable() {
        def memberInstance = Member.get(params.id)
        if (!memberInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'memberInstance.label', default: 'Member'), params.id])
            redirect(action: "list")
            return
        }

        memberInstance.status = Member.Status.ACTIVE
        if (memberService.update(memberInstance)) {
            flash.message = "คืนสิทธิสมาชิกเรียบร้อย"
            redirect(action: "show", id: memberInstance.id)
            return
        } else {
            flash.message = message(code: "member.update.failed", default: "Update Failed.")
            memberInstance.errors.rejectValue("status", "member.enable.fail",
                    [message(code: 'member.label', default: 'Member')] as Object[],
                    "Enable failed.")
            render(view: "edit", model: [memberInstance: memberInstance])
            return
        }
    }
}

