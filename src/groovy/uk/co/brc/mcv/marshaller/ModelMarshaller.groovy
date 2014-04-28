package uk.co.brc.mcv.marshaller

import grails.converters.JSON
import uk.co.mcv.model.Model

/**
 * Created by soheil on 09/04/2014.
 */
class ModelMarshaller {
    void register() {
        JSON.registerObjectMarshaller( Model) { model ->
            return [
                    id : model.id,
                    name : model.name,
                    description : model.description
            ]
        }
    }
}

