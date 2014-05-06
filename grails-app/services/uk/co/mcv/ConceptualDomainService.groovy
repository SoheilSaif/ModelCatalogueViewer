package uk.co.mcv

import grails.transaction.Transactional
import uk.co.mcv.model.ConceptualDomain

@Transactional
class ConceptualDomainService {

	def modelService

	def delete(ConceptualDomain conceptualDomain){

		conceptualDomain.models.collect().each { model ->

			model.dataElements.collect().each { de ->
				model.removeFromDataElements(de)
				de.delete()
			}

			model.delete()
			conceptualDomain.removeFromModels(model)

		}

		conceptualDomain.save()
		//conceptualDomain.delete()
	}

}
