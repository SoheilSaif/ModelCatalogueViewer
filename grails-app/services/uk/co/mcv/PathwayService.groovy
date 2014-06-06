package uk.co.mcv

import uk.co.mcv.pathway.Pathway

class PathwayService {

	static transactional = false

	void delete(Pathway pathway){
		pathway.delete()
	}

	Pathway create(Map pathwayParams) {
		Pathway pathway = new Pathway(pathwayParams)
		pathway.save(failOnError: true) // bubble up the exception to the controller
		return pathway
	}
	/**
	 * Return a list of top-level pathways, with a given set of search criteria.
	 * The structure of the searchCriteria should be (e.g.):
	 *
	 * {
	 *  name: "pathway 1",
	 *  isDraft: false
	 * }
	 *
	 * @param searchCriteria The search criteria
	 * @return a list of pathways (top level only)
	 */
	List<Pathway> topLevelPathways(Map searchCriteria) {

		List<Pathway> pathways
		if(searchCriteria == null){
			pathways = Pathway.list()
		}else{

			def nodeProps = Pathway.metaClass.properties*.name
			pathways = Pathway.withCriteria {
				and {
					searchCriteria.each { field, value ->
						if (nodeProps.grep(field)) {
							eq(field, value)
						}
					}
				}
			}
		}
		// FIXME this should be in the criteria, but I had problems getting that to work :(
		// This is really problematic because we want to leverage Pathway.list(params) to get offset and max
		return pathways.findAll { it.class == Pathway }

	}

	Pathway get(def id) {
		Pathway.get id
	}
}
