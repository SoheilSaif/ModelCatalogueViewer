class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }


		//returns all models
        "/api/models"(version:'1.0', resources:"Model", namespace:'v1')


		//nested resources for models
		"/api/models"(version:'1.0', resources:"Model", namespace:'v1') {

			//returns all dataElements of a model like models/1/dataElements
			"/dataelements"(version:'1.0', resources:"DataElement", namespace:'v1')

		}

		//returns all dataElements
        "/api/dataelements"(version:'1.0', resources:"DataElement", namespace:'v1')



        "/"(view:"/index")
        "500"(view:'/error')
	}
}