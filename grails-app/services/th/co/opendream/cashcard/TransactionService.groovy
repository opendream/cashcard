package th.co.opendream.cashcard

class TransactionService {

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
                remainder: 0.00
            )

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
        def outstanding = amount
        if (Policy.isDeferredCompoundMethod()) {
            if (outstanding >= member.balance) {
                throw RuntimeException("Not enought balance.")
            }
            member.balance -= outstanding
            outstanding = 0.00
        }
        else {
            if (outstanding >= member.interest) {
                if (Policy.isCompoundMethod()) {
                    // do nothing
                } else {
                    outstanding -= member.interest
                }
                member.interest = 0.00
            } else {
                if (Policy.isCompoundMethod()) {
                    // do nothing
                    member.interest -= outstanding
                } else {
                    member.interest -= outstanding
                    outstanding = 0.00
                }
            }
            if (outstanding > member.balance) {
                outstanding -= member.balance
                member.balance = 0.00
            } else {
                member.balance -= outstanding
                outstanding = 0.00
            }
        }

        def net = amount - outstanding
        def balance = new BalanceTransaction(
            amount: amount,
            date: new Date(),
            txType: TransactionType.DEBIT,
            member: member,
            activity: ActivityType.PAYMENT,
            net: net,
            remainder: outstanding
        )



        if (!balance.save()) {
            throw new RuntimeException("Fail to save transaction record")
        } else {
            if (! member.save()) {
               throw new RuntimeException("Fail to save member record")
            }
        }

        balance
    }
}
