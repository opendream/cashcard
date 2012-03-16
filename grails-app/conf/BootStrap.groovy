import th.co.opendream.cashcard.Company
import th.co.opendream.cashcard.Member
import th.co.opendream.cashcard.Policy
import th.co.opendream.cashcard.Role
import th.co.opendream.cashcard.Users
import th.co.opendream.cashcard.UsersRole
import th.co.opendream.cashcard.InterestTransaction
import th.co.opendream.cashcard.BalanceTransaction
import th.co.opendream.cashcard.Transaction
import th.co.opendream.cashcard.TransactionType
import th.co.opendream.cashcard.ActivityType
import groovy.time.*
import static java.util.Calendar.*
import grails.util.Environment


class BootStrap {

    def init = { servletContext ->

        def currentEnv = Environment.current

        if (currentEnv == Environment.DEVELOPMENT || currentEnv == Environment.CUSTOM) {
            def opendream = new Company(name:'opendream', address:'bkk', taxId:'1-2-3-4')
            def user = new Users(username:'admin', password:'password',
                                firstname:'admin', lastname:'messenger',
                                email:'admin@messenger.opendream.org', enabled:true)
            opendream.addToUsers(user)
            opendream.save(failOnError: true)
            def role = new Role(authority:'ROLE_ADMIN').save(failOnError: true)
            new UsersRole(user:user, role:role).save(failOnError: true)

            def m1 = new Member(identificationNumber:"1159900100015", firstname:"Nat", lastname: "Weerawan", telNo: "111111111", gender: "MALE", address: "Opendream")
            def m2 = new Member(identificationNumber: "1234567891234", firstname: "Noomz", lastname: "Siriwat", telNo: "111111111", gender: "MALE", address: "Opendream2")

            m1.save()
            m2.save()



            new Policy(key: Policy.KEY_CREDIT_LINE, value: 2000000000).save() // 2 billion baht limit
            new Policy(key: Policy.KEY_INTEREST_METHOD, value: Policy.VALUE_NON_COMPOUND).save()
            new Policy(key: Policy.KEY_INTEREST_RATE_LIMIT, value: '18.00').save()

            def today = Calendar.instance
            today = today.time

            new InterestTransaction([id: 1, member: m1, amount: 10.00, txType: TransactionType.CREDIT, date: today, fee: 0.00, interest: 10.00]).save()
            new InterestTransaction([id: 2, member: m2, amount: 21.00, txType: TransactionType.CREDIT, date: today, fee: 0.00, interest: 21.00]).save()
            new InterestTransaction([id: 3, member: m1, amount: 13.00, txType: TransactionType.CREDIT, date: today.minus(1), fee: 0.00, interest: 13.00]).save()
            new InterestTransaction([id: 4, member: m2, amount: 24.00, txType: TransactionType.CREDIT, date: today.minus(1), fee: 0.00, interest: 24.00]).save()

            new BalanceTransaction([id: 1, member: m1, amount: 500.00, txType: TransactionType.CREDIT, activity: ActivityType.WITHDRAW, date: today, net: 500.00, remainder: 0.00]).save()
            new BalanceTransaction([id: 2, member: m2, amount: 700.00, txType: TransactionType.CREDIT, activity: ActivityType.WITHDRAW, date: today, net: 700.00, remainder: 0.00]).save()
            new BalanceTransaction([id: 3, member: m1, amount: 200.00, txType: TransactionType.CREDIT, activity: ActivityType.WITHDRAW, date: today.minus(1), net: 200.00, remainder: 0.00]).save()
            new BalanceTransaction([id: 4, member: m2, amount: 300.00, txType: TransactionType.DEBIT, activity: ActivityType.PAYMENT, date: today.minus(1), net: 300.00, remainder: 0.00]).save()
            //new BalanceTransaction([id: 5, member: m1, amount: 707.50, txType: TransactionType.DEBIT, activity: ActivityType.PAYMENT, date: today.minus(2), net: 707.32, remainder: 0.18]).save()
            new BalanceTransaction([id: 6, member: m2, amount: 405.25, txType: TransactionType.DEBIT, activity: ActivityType.PAYMENT, date: today.minus(2), net: 405.08, remainder: 0.17]).save()
        }

    }

    def destroy = {
    }
}
