class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(controller:"login", action:"auth")
		"/member"(controller:"member", action: "list")
		"/report"(controller:"report", action: "dailySummary")
		"500"(view:'/error')
	}
}
