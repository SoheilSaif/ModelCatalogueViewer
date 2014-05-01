package uk.co.brc.mcv.marshaller;

import grails.converters.JSON
import grails.converters.XML
import uk.co.mcv.pathway.Pathway


public class PathwayMarshaller {

	void register() {
        def marshaller = { Pathway pathway ->

            return [
                'id' : pathway.id,
                'name': pathway?.name,
                'catalogueVersion': pathway?.userVersion,
                'isDraft': pathway?.isDraft,
                'description'	: pathway?.description,
                'nodes' : pathway.getNodes(),
                'links' : pathway.getLinks(),
                'version' : pathway.version,
            ]
        }

        JSON.registerObjectMarshaller(Pathway) { Pathway pathway ->
            return marshaller(pathway)
        }

        XML.registerObjectMarshaller(Pathway) { Pathway pathway ->
            return marshaller(pathway)
        }
	}
}


