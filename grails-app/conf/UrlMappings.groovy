class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(controller:"member", action:"verifyCard")
		"500"(view:'/error')
	}
}
