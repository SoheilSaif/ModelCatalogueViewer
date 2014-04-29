package uk.co.brc.mcv.marshaller

import grails.converters.JSON
import uk.co.mcv.model.DataElement
import uk.co.mcv.model.Model

/**
 * Created by soheil on 09/04/2014.
 */
class DataElementMarshaller {
    void register() {
        JSON.registerObjectMarshaller( DataElement) { dataElement ->
            return [
                    id : dataElement.id,
                    name : dataElement.name,
                    description : dataElement.description,
					definition: dataElement.definition,
					valueDomain : dataElement?.valueDomain,
					catalogueVersion: dataElement.catalogueVersion,
					catalogueId: dataElement.catalogueId,
					modelId: dataElement?.model.id,
					modelName: dataElement?.model?.name
			]
        }
    }
}

