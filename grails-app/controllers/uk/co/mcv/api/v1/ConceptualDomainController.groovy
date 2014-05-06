package uk.co.mcv.api.v1

import uk.co.mcv.BetterRestfulController
import uk.co.mcv.model.ConceptualDomain

class ConceptualDomainController extends BetterRestfulController{
	static namespace = "v1"

	def ConceptualDomainController() {
		super(ConceptualDomain,true)
	}
}
