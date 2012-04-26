package th.co.opendream.cashcard

class UserProfileController {

	def springSecurityService

    def index() {
    	redirect action: 'show'
    }

    def show() {
    	def userId = springSecurityService.principal.id
    	def model = Users.get(userId)
    	[user:model]
    }

    def edit() {
    	def userId = springSecurityService.principal.id
    	def model = Users.get(userId)
    	[user:model]
    }

    def update() {
    	def userId = springSecurityService.principal.id
    	def user = Users.get(userId)
    	def oldpassword = user.password
    	def password = params.password

    	if(oldpassword != springSecurityService.encodePassword(password)) {
    		user.password = password
    	}

    	if(!user.save(flush: true)) {
			redirect action: 'edit'
			return
		}

    	redirect action: 'show'
    }
}
