class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/api/models"(version:'1.0', resources:"Model", namespace:'v1')


        "/api/dataelements"(version:'1.0', resources:"DataElement", namespace:'v1')
//
        "/api/models"(version:'1.0', resources:"Model", namespace:'v1') {
            "/dataelements"(version:'1.0', resources:"DataElement", namespace:'v1')
        }



        "/"(view:"/index")
        "500"(view:'/error')
	}
}
