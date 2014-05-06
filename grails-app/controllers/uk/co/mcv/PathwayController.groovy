package uk.co.mcv

import grails.rest.RestfulController
import org.springframework.dao.DataIntegrityViolationException
import uk.co.mcv.pathway.Pathway

class PathwayController extends BetterRestfulController<Pathway>{

	def pathwayService

	PathwayController() {
		super(Pathway)
	}

	static defaultAction = "index"

	@Override
	protected List<Pathway> listAllResources(Map params) {
		pathwayService.topLevelPathways(params)
	}

	@Override
	protected Integer countResources(params) {
		pathwayService.topLevelPathways(params).size()
	}


	@Override
	def show(){
		Pathway pathway
		pathway = pathwayService.get(params.id)

		if(pathway){
			respond pathway, [model: [pathway: pathway]]
		}else{
			def model = [ success: false, msg: [ code: 404, text: "The item could not be found or you do not have access to it"]]
			respond model as Object, [status: 404, view: 'error404']
		}
	}

	/**
	 * Creates a new instance of the resource for the given parameters, or throws an exception.
	 *
	 * @param params The Map representing the new pathway
	 * @throws grails.validation.ValidationException if the pathway could not be created
	 * @return The resource instance
	 */
	protected Pathway createResource(Map params) {
		pathwayService.create(params)
	}


	def delete(Pathway pathway) {

		def model
		def msg

		if (!pathway) {
			msg = message(code: 'default.not.found.message', args: [message(code: 'pathway.label', default: 'Pathway'), params.id])
			model = [errors: true, details: msg]

		}else{
			try {
				pathwayService.delete(pathway)
				msg = message(code: 'default.deleted.message', args: [message(code: 'pathway.label', default: 'Pathway'), pathway.id])
				model = [success: true, details: msg]
			}
			catch (DataIntegrityViolationException e) {
				msg = message(code: 'default.not.deleted.message', args: [message(code: 'pathway.label', default: 'Pathway'), pathway.id])
				model = [errors: true, details: msg]
			}
		}

		respond model
	}

	/**
	 * Utility method to return the current pathway instance. Uses PathwayService to honor security.
	 * @return the pathway or null.
	 */
	private Pathway findInstance() {
		return findInstance(params.long('id'))
	}

	/**
	 * Utility method to return the current pathway instance. Uses PathwayService to honour security.
	 * @return the pathway or null.
	 */
	private Pathway findInstance(Long id) {
		def pathway = pathwayService.get(id)
		if (!pathway) {
			flash.message = "Pathway not found with id $params.id"
		}
		pathway
	}
}