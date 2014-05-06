package uk.co.brc.mcv.marshaller

import grails.converters.JSON
import uk.co.mcv.model.ConceptualDomain
import uk.co.mcv.model.Model

/**
 * Created by soheil on 06/05/2014.
 */
class ConceptualDomainMarshaller {
	void register() {
		JSON.registerObjectMarshaller( ConceptualDomain) { conceptualDomain ->
			return [
					id : conceptualDomain.id,
					name : conceptualDomain.name,
					description : conceptualDomain.description,
					catalogueVersion: conceptualDomain.catalogueVersion,
					catalogueId: conceptualDomain.catalogueId,
					models: conceptualDomain?.models,
			]
		}
	}
}
