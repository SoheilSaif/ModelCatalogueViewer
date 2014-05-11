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

			def allValueDomains = []
			def valueDomain = null

			//as valueDomain and DataElement has a many-to-many relationship through dataElementValueDomains
			//so we need to just extract its valueDomains and return them
			//BUT it's better to return the valueDomain of the same conceptualDomain that
			//the dataElement belongs to
			dataElement.dataElementValueDomains.each{ dv ->

				//returns all valueDomains
				allValueDomains.add(dv.valueDomain)

				//finds and returns the actual valueDomain which belongs to the same conceptualDomain
				if(dataElement?.model?.conceptualDomain?.id == dv.valueDomain.conceptualDomain.id)
					valueDomain = dv.valueDomain
			}


            return [
                    id : dataElement.id,
                    name : dataElement.name,
                    description : dataElement.description,
					definition: dataElement.definition,
					allValueDomains : allValueDomains,
					valueDomain : valueDomain,
					catalogueVersion: dataElement.catalogueVersion,
					catalogueId: dataElement.catalogueId,
					modelId: dataElement?.model.id,
					modelName: dataElement?.model?.name
			]
        }
    }
}