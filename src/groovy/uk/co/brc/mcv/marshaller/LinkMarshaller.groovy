package uk.co.brc.mcv.marshaller

import grails.converters.JSON
import grails.converters.XML
import uk.co.mcv.pathway.Link

class LinkMarshaller {

	void register() {
		def marshaller = { Link link ->
				
			return [
                'id' : link?.id,
                'source': link?.source?.id,
                'target': link?.target?.id,
                'version' : link?.version,
                'name': link?.name,
                'description': link?.description
			]
		}

        JSON.registerObjectMarshaller(Link) { Link link ->
            return marshaller(link)
        }

        XML.registerObjectMarshaller(Link) { Link link ->
            return marshaller(link)
        }
	}
	
}
