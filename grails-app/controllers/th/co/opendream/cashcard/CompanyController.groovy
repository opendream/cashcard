package th.co.opendream.cashcard

class CompanyController {
	def companyService

    def search() { 
    	render view: 'search'
    }

    def create() {
    	[company: new Company(params)]
    }

    def save = {
    	try {
			def company = new Company(params)
			companyService.save(company)
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'company.label', default: 'Company'), company.id])}"
			redirect action: 'edit', id: company.id
		} catch(e) {
			log.error e
			render view: 'create', model: [company: company]	         
		}
	}

    def edit() {
    	def company = params.name ? Company.findByName(params.name) : null
    	if(!company) {
    		company = Company.get(params.id)
    	}
    	if(!company) {
    		return
    	}
    	[company:company, users:company.users, member:company.members]
    }

    def companySearch = {
		params.offset = params.offset ? params.int('offset') : 0
        params.max = params.max ? params.int('max') : 10

		def c = Company.createCriteria()
        def companyList = c.list(offset: params.offset, max: params.max) {
            if (params.name) {
                ilike('name', '%' + params.name + '%')
            }
            if (params.address) {
                ilike('address', '%' + params.address + '%')
            }
            if (params.taxId) {
                ilike('taxId' , '%' + params.taxId + '%')
            }
        }
		

		def model = [results: companyList, totalCount: companyList.totalCount, searched: true]

		for (name in ['name', 'address', 'taxId', 'sort', 'order']) {
		 	model[name] = params[name]
		}

		render view: 'search', model: model
	}

	def update() {
		def company = Company.get(params.id)
		if (!company) return
		if (!(company.version < params.version)) {
			return
		}
		company.properties = params
		if (!company.save(flush:true)) {
			render view: 'edit', model: [company:company, users:company.users, member:company.members]
			return
		}

		flash.message = "${message(code: 'default.updated.message', args: [message(code: 'company.label', default: 'Company'), company.id])}"
		redirect action: 'edit', id: company.id
	}
}
