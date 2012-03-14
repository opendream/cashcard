package th.co.opendream.cashcard

class CompanyTagLib {
	static namespace = 'com'
	def springSecurityService
	
	def name = { 
		println "principal " + springSecurityService?.principal
		println "companyName " +springSecurityService?.principal?.companyName
		out << springSecurityService?.principal?.companyName
	}
}
