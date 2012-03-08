package th.co.opendream.cashcard

class TransactionService {

    def withdraw(Member member, amount) {

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
        else {
            return balance
        }
    }

    def pay(Member member, amount, net) {

        if (amount < net) {
            throw new RuntimeException("Fail to save transaction record")
        }

        def balance = new BalanceTransaction(
            amount: amount,
            date: new Date(),
            txType: TransactionType.DEBIT,
            member: member,
            activity: ActivityType.PAYMENT,
            net: net,
            remainder: amount - net
        )

        if (!balance.save()) {
            throw new RuntimeException("Fail to save transaction record")
        }
        else {
            return balance
        }
    }
}
