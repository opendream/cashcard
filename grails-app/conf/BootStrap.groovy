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
import grails.util.Environment
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class BootStrap {
    def companyService
    def schemaService
    def init = { servletContext ->
        if (Environment.currentEnvironment == Environment.DEVELOPMENT ||
            Environment.currentEnvironment == Environment.TEST) {

            def opendream = new Company(name:'opendream', address:'bkk', taxId:'1-2-3-4')
            def policy1 = new Policy(key: Policy.KEY_CREDIT_LINE, value: "2000.00", company: opendream)
            def policy2 = new Policy(key: Policy.KEY_INTEREST_METHOD, value: Policy.VALUE_NON_COMPOUND)
            def policy3 = new Policy(key: Policy.KEY_INTEREST_RATE_LIMIT, value: '18.00')

            opendream
                .addToPolicy(policy1)
                .addToPolicy(policy2)
                .addToPolicy(policy3)

            def opendream2 = new Company(name:'opendream2', address:'bkk', taxId:'1-2-3-4')
            def policy4 = new Policy(key: Policy.KEY_CREDIT_LINE, value: "4000.00", company: opendream)
            def policy5 = new Policy(key: Policy.KEY_INTEREST_METHOD, value: Policy.VALUE_COMPOUND)
            def policy6 = new Policy(key: Policy.KEY_INTEREST_RATE_LIMIT, value: '18.00')
            opendream2
                .addToPolicy(policy4)
                .addToPolicy(policy5)
                .addToPolicy(policy6)

            def user = new Users(username:'admin', password:'password', 
                                , enabled:true, accountExpired:false, accountLocked:false, passwordExpired:false)

        	def m1 = new Member(identificationNumber:"1159900100015", firstname:"Nat", lastname: "Weerawan", telNo: "111111111", gender: "MALE", address: "Opendream")
        	def m2 = new Member(identificationNumber: "1234567891234", firstname: "Noomz", lastname: "Siriwat", telNo: "111111111", gender: "MALE", address: "Opendream2")

            opendream.addToUsers(user)
            opendream.addToMembers(m1)     
            companyService.save(opendream)

            opendream2.addToMembers(m2)
            companyService.save(opendream2)

            def roleAdmin = new Role(authority:'ROLE_ADMIN').save(failOnError: true)
            def roleCounter = new Role(authority:'ROLE_COUNTER').save(failOnError: true)
            def roleUser = new Role(authority:'ROLE_USER').save(failOnError: true)
            new UsersRole(user:user, role:roleAdmin).save(failOnError: true)
            new UsersRole(user:user, role:roleCounter).save(failOnError: true)
            new UsersRole(user:user, role:roleUser).save(failOnError: true) 
        }

        createRequestMaps();
        checkSessionTimeout()
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

    def checkSessionTimeout() {
        def basePath = System.properties['base.dir']
        switch (Environment.currentEnvironment) {
          case Environment.DEVELOPMENT:
            if (new File("/${basePath}/src/templates/war/web_dev.xml").exists()) {
                CH.config.base.webXml = "file:${basePath}/src/templates/war/web_dev.xml"
            }
            break;
          default:
            if (new File("/${basePath}/src/templates/war/web_prod.xml").exists()) {
                CH.config.base.webXml = "file:${basePath}/src/templates/war/web.xml"
            }
            break;
        }
    }
}
