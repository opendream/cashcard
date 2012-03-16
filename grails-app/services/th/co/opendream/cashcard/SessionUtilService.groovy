package th.co.opendream.cashcard

class SessionUtilService {
	def springSecurityService

    def getUser() {
    	Users.get(springSecurityService?.principal?.id)
    }

    def getCompany() {
    	Company.get(springSecurityService?.principal?.companyId)
    }
}
