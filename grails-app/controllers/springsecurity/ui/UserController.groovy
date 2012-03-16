package springsecurity.ui

import grails.util.GrailsNameUtils

import th.co.opendream.cashcard.Company
import org.codehaus.groovy.grails.plugins.springsecurity.NullSaltSource
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class UserController extends grails.plugins.springsecurity.ui.UserController {
	def springSecurityService

	def create = {
		def user = lookupUserClass().newInstance(params)
		[user: user, authorityList: sortedRoles(), companyList: sortedCompanies()]
	}

	def save = {
		def user = lookupUserClass().newInstance(params)
		if (params.password) {
			String salt = saltSource instanceof NullSaltSource ? null : params.username
			//user.password = springSecurityUiService.encodePassword(params.password, salt)
		}
		if(!user.validate() || !params.containsKey('companyId') || !params.keySet().find {it.contains('ROLE')}) {
			render view: 'create', model: [user: user, authorityList: sortedRoles(), companyList: sortedCompanies()]
			return
		}
		addCompany(user, params.companyId)
		addRoles(user)		
		flash.message = "${message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), user.id])}"
		redirect action: edit, id: user.id
	}

	def update = {
		String passwordFieldName = SpringSecurityUtils.securityConfig.userLookup.passwordPropertyName

		def user = findById()
		if (!user) return
		if (!versionCheck('user.label', 'User', user, [user: user])) {
			return
		}

		def oldPassword = user."$passwordFieldName"
		user.properties = params
		if (params.password && !params.password.equals(oldPassword)) {
			String salt = saltSource instanceof NullSaltSource ? null : params.username
			user."$passwordFieldName" = springSecurityUiService.encodePassword(params.password, salt)
		}


		if (!user.save(flush: true)) {
			render view: 'edit', model: buildUserModel(user)
			return
		}


		String usernameFieldName = SpringSecurityUtils.securityConfig.userLookup.usernamePropertyName

		lookupUserRoleClass().removeAll user		
		addRoles(user)
		romeveCompany(user)
		addCompany(user, params.companyId)
		userCache.removeUserFromCache user[usernameFieldName]
		flash.message = "${message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), user.id])}"
		redirect action: edit, id: user.id
	}

	protected void addRoles(user) {
		String upperAuthorityFieldName = GrailsNameUtils.getClassName(
			SpringSecurityUtils.securityConfig.authority.nameField, null)
		for (String key in params.keySet()) {
			if (key.contains('ROLE') && 'on' == params.get(key)) {
				lookupUserRoleClass().create user, lookupRoleClass()."findBy$upperAuthorityFieldName"(key), true
			}
		}
	}

	protected def addCompany(user, companyId) {
		def company = Company.get(companyId)
		company.addToUsers(user)
		return company.save(flush:true)		
	}

	protected def romeveCompany(user) {
		def company = user.company
		company.removeFromUsers(user)
		return company.save(flush:true)		
	}

	protected List sortedCompanies() {
		def companies = []
		
		def roles = grailsApplication.config.company.management.authority.tokenize(', ')
		//get user principal
		def userPrincipal = springSecurityService.principal

		if(userPrincipal.authorities.intersect(roles)) {
			companies = Company.list(order: 'name')
		} else {			
			companies = [Company.get(userPrincipal.companyId)]
		}

		return companies
	}

	protected Map buildUserModel(user) {

		String authorityFieldName = SpringSecurityUtils.securityConfig.authority.nameField
		String authoritiesPropertyName = SpringSecurityUtils.securityConfig.userLookup.authoritiesPropertyName

		List roles = sortedRoles()
		Set userRoleNames = user[authoritiesPropertyName].collect { it[authorityFieldName] }
		def granted = [:]
		def notGranted = [:]
		for (role in roles) {
			String authority = role[authorityFieldName]
			if (userRoleNames.contains(authority)) {
				granted[(role)] = userRoleNames.contains(authority)
			}
			else {
				notGranted[(role)] = userRoleNames.contains(authority)
			}
		}

		return [user: user, roleMap: granted + notGranted, companyList: sortedCompanies()]
	}

}
