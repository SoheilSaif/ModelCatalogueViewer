package uk.co.brc.mcv.marshaller

import grails.converters.JSON
import uk.co.mcv.model.DataType
import uk.co.mcv.model.ValueDomain

/**
 * Created by soheil on 09/04/2014.
 */
class DataTypeMarshaller {
    void register() {
        JSON.registerObjectMarshaller( DataType) { dataType ->
            return [
                    id : dataType.id,
					name: dataType.name,
					//enumerated: dataType.enumerated,
					//enumerations: dataType.enumerations,
					catalogueVersion: dataType.catalogueVersion,
					catalogueId: dataType.catalogueId
			]
        }
    }
}

