package uk.co.brc.mcv.marshaller

import grails.converters.JSON
import uk.co.mcv.model.ConceptualDomain
import uk.co.mcv.model.DataElement
import uk.co.mcv.model.DataType
import uk.co.mcv.model.ValueDomain

/**
 * Created by soheil on 09/04/2014.
 */
class ValueDomainMarshaller {
    void register() {
        JSON.registerObjectMarshaller( ValueDomain) { valueDomain ->
            return [
                    id : valueDomain.id,
                    name : valueDomain.name,
					unitOfMeasure: valueDomain.unitOfMeasure,
					regexDef: valueDomain.regexDef,
					format: valueDomain.format,
					description: valueDomain.description,
					dataType: valueDomain?.dataType
			]
        }
    }
}

