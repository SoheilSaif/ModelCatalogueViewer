package uk.co.mcv

import grails.transaction.Transactional
import uk.co.mcv.model.ConceptualDomain
import uk.co.mcv.model.ValueDomain

@Transactional
class ConceptualDomainService {

	def modelService

	def deleteConceptualDomain(ConceptualDomain conceptualDomain) {

		//remove models (call modelService.deleteModel)
		conceptualDomain.models.collect().each { model ->
			conceptualDomain.removeFromModels(model)
			modelService.deleteModel(model)
		}
		conceptualDomain.models.clear()

		//remove valueDomains from conceptualDomain
		//remove valueDomain from dataType
		conceptualDomain.valueDomains.collect().each {ValueDomain vd ->
			//remove valueDomain from its DataType
			vd.dataType.removeFromValueDomains(vd)
			conceptualDomain.removeFromValueDomains(vd)
			vd.delete()
		}
		conceptualDomain.valueDomains.clear()

		conceptualDomain.delete()
	}
}
