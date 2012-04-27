package th.co.opendream.cashcard

class CompanyController {
	static defaultAction = "search"
	def companyService

    def search() { 
    	
    }

    def create() {
    	[company: new Company(params)]
    }

    def save() {
    	def company = new Company(params)
    	try {		
    		def policies = validatePolicy()    		
    		if(policies) {
    			policies.each {
    				company.addToPolicy(it)
    			}
    		}
    		
    		companyService.save(company)
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'company.label', default: 'Company'), company.id])}"
			redirect action: 'edit', id: company.id 
			
		} catch(e) {
			log.error e
			render view: 'create', model: [company: company, policyKeyInterestMethod:policyKeyInterestMethod, policyKeyInterestRateLimit:policyKeyInterestRateLimit, policyKeyCreditLine:policyKeyCreditLine]	         
		}
	}

    def edit() {
    	def company = params.name ? Company.findByName(params.name) : null
    	def policyKeyInterestMethod
    	def policyKeyInterestRateLimit
    	def policyKeyCreditLine

    	if(!company) {
    		company = Company.get(params.id)
    		def policies = company.policy
    		policyKeyInterestMethod = policies.find { it.key == 'InterestMethod'}.value    		
    		policyKeyInterestRateLimit = policies.find { it.key == 'InterestRateLimit'}.value
    		policyKeyCreditLine = policies.find { it.key == 'CreditLine'}.value
    	}
    	if(!company) {
    		return
    	}
    	[company:company, users:company.users, members:company.members, policyKeyInterestMethod:policyKeyInterestMethod, policyKeyInterestRateLimit:policyKeyInterestRateLimit, policyKeyCreditLine:policyKeyCreditLine]
    }

    def update() {
		def company = Company.get(params.id)
		if (!company) return
		if (company.version > params.long('version')) {
			return
		}
		company.properties = params
		def policies = validatePolicy()    		
    		if(policies) {    			
    			policies.each { newPolicy ->
    				def currentPolicy = company.policy.find { it.key == newPolicy.key}
    				currentPolicy.value = newPolicy.value    				
    			}
    		}

		if (!company.save(flush:true)) { 
			println 'fail'
			render view: 'edit', model: [company:company, users:company.users, members:company.members]
			return
		}

		flash.message = "${message(code: 'default.updated.message', args: [message(code: 'company.label', default: 'Company'), company.id])}"
		redirect action: 'edit', id: company.id
	}

	def companySearch() {
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

	protected def validatePolicy() {
		def policies
		if( params.policyKeyInterestMethod && params.float('policyKeyInterestRateLimit') &&
    		params.float('policyKeyCreditLine') ) { 
    		policies = []
    		policies.add (new Policy(key: Policy.KEY_CREDIT_LINE, value: params.policyKeyCreditLine))
            policies.add (new Policy(key: Policy.KEY_INTEREST_METHOD, value: params.policyKeyInterestMethod))
            policies.add (new Policy(key: Policy.KEY_INTEREST_RATE_LIMIT, value: params.policyKeyInterestRateLimit))	

    	}
    	policies
	}
}
