package uk.co.mcv

import grails.transaction.Transactional
import uk.co.mcv.model.Model

@Transactional
class ModelService {

    def deleteModel(Model model) {
		model.dataElements.collect().each { de ->
			model.removeFromDataElements(de)
		}
		model.delete()
	}
}
