package th.co.opendream.cashcard

class TransactionService {

    def withdraw(Member member, amount) {
        amount = amount as BigDecimal
        if (amount <= 0) {
           throw new RuntimeException(message: "Withdraw amount is less than or equal 0 : ${amount}")
        }

        if (member.canWithdraw(amount)) {    
            member.balance += amount

            if (member.validate()) {

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
                    member.save()
                }
            }
            else {
                return false
            }
        }
        else {
            return false
        }
    }
}
