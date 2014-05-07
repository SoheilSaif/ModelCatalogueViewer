package uk.co.mcv.api.v1

import grails.converters.JSON
import uk.co.mcv.BetterRestfulController
import uk.co.mcv.model.ConceptualDomain

class ConceptualDomainController extends BetterRestfulController{
	static namespace = "v1"

	def conceptualDomainService

	def ConceptualDomainController() {
		super(ConceptualDomain,true)
	}

	@Override
	protected  List<ConceptualDomain> listAllResources(Map params) {
		conceptualDomainService.listAll(params)

	}

	@Override
	protected Integer countResources(params) {
		conceptualDomainService.countAll(params)
	}
}
