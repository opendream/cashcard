package th.co.opendream.cashcard

class TransactionService {
    def sessionUtilService,
        policyService

    def withdraw(Member member, amount) {
        if (amount <= 0) {
           throw new RuntimeException(message: "Withdraw amount is less than or equal 0 : ${amount}")
        }

        def canWithdraw = member.canWithdraw(amount);
        if (canWithdraw) {
            member.balance += amount

            def balance = new BalanceTransaction(
                amount: amount,
                date: new Date(),
                txType: TransactionType.CREDIT,
                member: member,
                activity: ActivityType.WITHDRAW,
                net: amount,
                remainder: 0.00,
                userCompany: sessionUtilService.company,
                memberCompany: member.company,
                balance: member.balance,
                balance_pay: 0.00,
                interest_pay: 0.00
            )
            if(sessionUtilService.company!=member.company) {
                balance.transferType = TransferType.SENT
            }

            if (!balance.save()) {
                throw new RuntimeException("Fail to save transaction record")
            }

            if (!member.save()) {
                throw new RuntimeException("Fail to update member balance")
            }

            return balance

        } else {
            throw new RuntimeException('amount over credit')
        }
    }

    /**
     *  amount -> ยอดเงินที่ตั้งใจจ่าย
     *
     */
    def pay(Member member, amount) {
        def outstanding = amount,
            balance_pay = 0.00,
            interest_pay = 0.00

        if (outstanding >= member.interest) {
            if (policyService.isCompoundMethod(member)) {
                // do nothing
            } else {
                outstanding -= member.interest
            }
            interest_pay = member.interest
            member.interest = 0.00
        } else {
            interest_pay = outstanding
            if (policyService.isCompoundMethod(member)) {
                // do nothing
                member.interest -= outstanding
            } else {
                member.interest -= outstanding
                outstanding = 0.00
            }
        }
        if (outstanding > member.balance) {
            balance_pay = member.balance
            outstanding -= member.balance
            member.balance = 0.00
        } else {
            balance_pay = outstanding
            member.balance -= outstanding
            outstanding = 0.00
        }

        def net = amount - outstanding
        def balance = new BalanceTransaction(
            amount: amount,
            date: new Date(),
            txType: TransactionType.DEBIT,
            member: member,
            activity: ActivityType.PAYMENT,
            net: net,
            remainder: outstanding,
            userCompany: sessionUtilService.company,
            memberCompany: member.company,
            balance: member.balance,
            balance_pay: balance_pay,
            interest_pay: interest_pay
            )
        if(sessionUtilService.company!=member.company) {
            balance.transferType = TransferType.SENT
        }

        if (!balance.save()) {
            throw new RuntimeException("Fail to save transaction record")
        } else {
            if (! member.save()) {
               throw new RuntimeException("Fail to save member record")
            }
        }

        balance
    }

    def settlement(begin, end) {
        def comsSettle = [:]
        def coms = Company.findAll().each {
            comsSettle[it.id] = 0.00
        }

        def results = Transaction.createCriteria().list {
            or {
                eq('transferType', TransferType.RECEIVE)
                eq('transferType', TransferType.SENT)
            }
            between('date', begin, end)
        }.each {
            if (it.txType == TransactionType.CREDIT) {
                if (it.transferType == TransferType.SENT) {
                    comsSettle[it.memberCompany.id] += it.amount
                } else if (it.transferType == TransferType.RECEIVE) {
                    comsSettle[it.userCompany.id] -= it.amount
                }
            } else {
                if (it.transferType == TransferType.SENT) {
                    comsSettle[it.memberCompany.id] -= it.amount
                } else if (it.transferType == TransferType.RECEIVE) {
                    comsSettle[it.userCompany.id] += it.amount
                }
            }

        }

        comsSettle

    }
}
