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
import th.co.opendream.cashcard.RequestMap
import groovy.time.*
import static java.util.Calendar.*



class BootStrap {
    def companyService
    def schemaService
    def init = { servletContext ->
        def opendream = new Company(name:'opendream', address:'bkk', taxId:'1-2-3-4')
        def opendreamx = new Company(name:'opendreamx', address:'bkk', taxId:'1-2-3-4')
        def user = new Users(username:'admin', password:'password', 
                            , enabled:true, accountExpired:false, accountLocked:false, passwordExpired:false)

    	def m1 = new Member(identificationNumber:"1159900100015", firstname:"Nat", lastname: "Weerawan", telNo: "111111111", gender: "MALE", address: "Opendream")
    	def m2 = new Member(identificationNumber: "1234567891234", firstname: "Noomz", lastname: "Siriwat", telNo: "111111111", gender: "MALE", address: "Opendream2")

    	//m1.save()
    	//m2.save()
        //user.save()
        opendream.addToUsers(user)
        opendream.addToMembers(m1)        
        companyService.save(opendream)

        opendreamx.addToMembers(m2)
        companyService.save(opendreamx)

        //opendream.save(failOnError: true)
        def roleAdmin = new Role(authority:'ROLE_ADMIN').save(failOnError: true)
        def roleCounter = new Role(authority:'ROLE_COUNTER').save(failOnError: true)
        def roleUser = new Role(authority:'ROLE_USER').save(failOnError: true)
        new UsersRole(user:user, role:roleAdmin).save(failOnError: true)
        new UsersRole(user:user, role:roleCounter).save(failOnError: true)
        new UsersRole(user:user, role:roleUser).save(failOnError: true)

        def today = Calendar.instance
            today = today.time

            /*new InterestTransaction([id: 1, member: m1, amount: 10.00, txType: TransactionType.CREDIT, date: today, fee: 0.00, interest: 10.00]).save()
            new InterestTransaction([id: 2, member: m2, amount: 21.00, txType: TransactionType.CREDIT, date: today, fee: 0.00, interest: 21.00]).save()
            new InterestTransaction([id: 3, member: m1, amount: 13.00, txType: TransactionType.CREDIT, date: today.plus(1), fee: 0.00, interest: 13.00]).save()
            new InterestTransaction([id: 4, member: m2, amount: 24.00, txType: TransactionType.CREDIT, date: today.plus(1), fee: 0.00, interest: 24.00]).save()
            
            new BalanceTransaction([id: 1, member: m1, amount: 500.00, txType: TransactionType.CREDIT, activity: ActivityType.WITHDRAW, date: today, net: 500.00, remainder: 0.00]).save()
            new BalanceTransaction([id: 2, member: m2, amount: 700.00, txType: TransactionType.CREDIT, activity: ActivityType.WITHDRAW, date: today, net: 700.00, remainder: 0.00]).save()
            new BalanceTransaction([id: 3, member: m1, amount: 200.00, txType: TransactionType.CREDIT, activity: ActivityType.WITHDRAW, date: today.plus(1), net: 200.00, remainder: 0.00]).save()
            new BalanceTransaction([id: 4, member: m2, amount: 300.00, txType: TransactionType.DEBIT, activity: ActivityType.PAYMENT, date: today.plus(1), net: 300.00, remainder: 0.00]).save()
            new BalanceTransaction([id: 5, member: m1, amount: 707.50, txType: TransactionType.DEBIT, activity: ActivityType.PAYMENT, date: today.plus(2), net: 707.32, remainder: 0.18]).save()
            new BalanceTransaction([id: 6, member: m2, amount: 405.25, txType: TransactionType.DEBIT, activity: ActivityType.PAYMENT, date: today.plus(2), net: 405.08, remainder: 0.17]).save() */  

            createRequestMaps();
    }

    def destroy = {
    }

    def createRequestMaps() {
        new RequestMap(url: '/js/**', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save()
        new RequestMap(url: '/css/**', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save()
        new RequestMap(url: '/images/**', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save()
        new RequestMap(url: '/login/**', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save()
        new RequestMap(url: '/logout/**', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save()
        new RequestMap(url: '/j_spring_security_switch_user',
                       configAttribute: 'ROLE_SWITCH_USER,IS_AUTHENTICATED_FULLY').save()
        new RequestMap(url: '/*', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save()
        new RequestMap(url: '/user/**', configAttribute: 'ROLE_ADMIN').save()
        new RequestMap(url: '/role/**', configAttribute: 'ROLE_ADMIN').save()
        new RequestMap(url: '/company/**', configAttribute: 'ROLE_ADMIN').save()
        new RequestMap(url: '/member/**', configAttribute: 'ROLE_USER,ROLE_COUNTER').save()
        new RequestMap(url: '/member/payment/**', configAttribute: 'ROLE_COUNTER').save()
        new RequestMap(url: '/member/pay/**', configAttribute: 'ROLE_COUNTER').save()
        new RequestMap(url: '/member/withdraw/**', configAttribute: 'ROLE_COUNTER').save()
        new RequestMap(url: '/interestRate/**', configAttribute: 'ROLE_USER,ROLE_COUNTER').save()
        new RequestMap(url: '/report/**', configAttribute: 'ROLE_USER,ROLE_COUNTER').save()
    }
}
