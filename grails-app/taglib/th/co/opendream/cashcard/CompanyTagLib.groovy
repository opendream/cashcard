package th.co.opendream.cashcard

class CompanyTagLib {
	static namespace = 'com'
	def springSecurityService
	
	def name = { 
		out << springSecurityService?.principal?.companyName
	}
}
