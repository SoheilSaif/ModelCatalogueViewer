package uk.co.brc.mcv.marshaller

import grails.converters.JSON
import uk.co.mcv.model.EnumeratedType

class EnumeratedTypeMarshaller extends DataTypeMarshaller {

	void register() {
		JSON.registerObjectMarshaller( EnumeratedType ) { enumerated ->

			return [
					id : enumerated.id,
					name: enumerated.name,
					catalogueVersion: enumerated.catalogueVersion,
					catalogueId : enumerated.catalogueId,
					enumerations: enumerated.enumerations
			]
		}
	}
}