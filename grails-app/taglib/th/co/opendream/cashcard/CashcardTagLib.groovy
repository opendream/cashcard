package th.co.opendream.cashcard

import org.springframework.web.servlet.support.RequestContextUtils as RCU

class CashcardTagLib {
	static namespace = "cashcard"
	
	def paginate = { attrs ->
		def writer = out
		if (attrs.total == null) {
			throwTagError("Tag [paginate] is missing required attribute [total]")
		}
		def messageSource = grailsAttributes.messageSource
		def locale = RCU.getLocale(request)

		def total = attrs.int('total') ?: 0
		def action = (attrs.action ? attrs.action : (params.action ? params.action : "list"))
		def offset = params.int('offset') ?: 0
		def max = params.int('max')
		def maxsteps = (attrs.int('maxsteps') ?: 10)

		if (!offset) offset = (attrs.int('offset') ?: 0)
		if (!max) max = (attrs.int('max') ?: 10)

		def linkParams = [:]
		if (attrs.params) linkParams.putAll(attrs.params)
		linkParams.offset = offset - max
		linkParams.max = max
		if (params.sort) linkParams.sort = params.sort
		if (params.order) linkParams.order = params.order

		def linkTagAttrs = [action:action]
		if (attrs.controller) {
			linkTagAttrs.controller = attrs.controller
		}
		if (attrs.id != null) {
			linkTagAttrs.id = attrs.id
		}
		if (attrs.fragment != null) {
			linkTagAttrs.fragment = attrs.fragment
		}
		linkTagAttrs.params = linkParams

		// determine paging variables
		def steps = maxsteps > 0
		int currentstep = (offset / max) + 1
		int firststep = 1
		int laststep = Math.round(Math.ceil(total / max))
		writer << '<ul>'
		// display previous link when not on firststep
		if (currentstep > firststep) {
			linkTagAttrs.class = 'prev'
			linkParams.offset = offset - max
			def str = link(linkTagAttrs.clone()) {
				 (attrs.prev ?: messageSource.getMessage('paginate.prev', null, messageSource.getMessage('default.paginate.prev', null, 'Previous', locale), locale))				
			}
			str = "<li>"+str+"</li>"
			writer << str
		}

		// display steps when steps are enabled and laststep is not firststep
		if (steps && laststep > firststep) {
			//linkTagAttrs.class = 'step'

			// determine begin and endstep paging variables
			int beginstep = currentstep - Math.round(maxsteps / 2) + (maxsteps % 2)
			int endstep = currentstep + Math.round(maxsteps / 2) - 1

			if (beginstep < firststep) {
				beginstep = firststep
				endstep = maxsteps
			}
			if (endstep > laststep) {
				beginstep = laststep - maxsteps + 1
				if (beginstep < firststep) {
					beginstep = firststep
				}
				endstep = laststep
			}

			// display firststep link when beginstep is not firststep
			if (beginstep > firststep) {
				linkParams.offset = 0
				def str = link(linkTagAttrs.clone()) {firststep.toString()}
				str= '<li class="prev disabled">'+str+'<li>'
				writer << str
				writer << '<li class="next">...</li>'
			}

			// display paginate steps
			(beginstep..endstep).each { i ->
				if (currentstep == i) {
					writer << "<li class='active'><a href='#'>$i</a></li>"
				}
				else {
					linkParams.offset = (i - 1) * max
					def str = link(linkTagAttrs.clone()) {i.toString()}
					str = '<li>'+str+'</li>'
					writer << str
				}
			}

			// display laststep link when endstep is not laststep
			if (endstep < laststep) {
				writer << '<li class="next">...</li>'
				linkParams.offset = (laststep -1) * max
				def str = link(linkTagAttrs.clone()) { laststep.toString() }
				str = '<li class="next">'+str+'</li>'
				writer << str
			}
		}

		// display next link when not on laststep
		if (currentstep < laststep) {
			linkTagAttrs.class = 'next'
			linkParams.offset = offset + max
			def str = link(linkTagAttrs.clone()) {				
				(attrs.next ? attrs.next : messageSource.getMessage('paginate.next', null, messageSource.getMessage('default.paginate.next', null, 'Next', locale), locale))				
			}
			str = "<li>"+str+"</li>"
			writer << str		
		}
		writer << '</ul>'
	}
}
