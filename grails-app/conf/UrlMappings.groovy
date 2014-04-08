class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }


        "/api/models"(version:'1.0', resources:"model", namespace:'v1')


        "/"(view:"/index")
        "500"(view:'/error')
	}
}
