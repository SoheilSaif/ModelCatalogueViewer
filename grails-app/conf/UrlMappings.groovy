class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }


		"/pathways"(resources: "pathway")
		"/api/pathways"(resources: "pathway")


		//ConceptualDomain REST API
		"/api/conceptualdomains"(version:'1.0', resources:"ConceptualDomain", namespace:'v1') {
			//returns all models of a specific conceptualDomain like conceptualdomains/1/models
			"/models"(version:'1.0', resources:"Model", namespace:'v1')
		}



		//Model REST API
        "/api/models"(version:'1.0', resources:"Model", namespace:'v1')



		//nested resources for models
		"/api/models"(version:'1.0', resources:"Model", namespace:'v1') {
			//returns all dataElements of a model like models/1/dataElements
			"/dataelements"(version:'1.0', resources:"DataElement", namespace:'v1')
		}



		//DataElements REST API
        "/api/dataelements"(version:'1.0', resources:"DataElement", namespace:'v1')



        "/"(view:"/index")
        "500"(view:'/error')
	}
}