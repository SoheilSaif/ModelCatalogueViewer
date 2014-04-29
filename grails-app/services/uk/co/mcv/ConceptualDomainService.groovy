package uk.co.mcv

import grails.transaction.Transactional
import uk.co.mcv.model.ConceptualDomain

@Transactional
class ConceptualDomainService {

	def getNHIC() {
		if(ConceptualDomain.count() == 0)
			new ConceptualDomain(name: "NHIC",description: "NHIC Model Catalogue",catalogueId: "1",catalogueVersion: "1").save(failOnError: true)

		return ConceptualDomain.first()
	}

	def getModels(ConceptualDomain conceptualDomain) {
		conceptualDomain.models
	}
}
