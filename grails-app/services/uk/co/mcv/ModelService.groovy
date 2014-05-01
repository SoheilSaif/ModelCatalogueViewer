package uk.co.mcv

import grails.transaction.Transactional
import uk.co.mcv.model.Model

@Transactional
class ModelService {

    def deleteModel(Model model) {
		model.dataElements.collect().each { de ->
			model.removeFromDataElements(de)
		}
		model.subModels.collect().each { subModel ->
			model.removeFromSubModels(subModel)
		}
		model.delete()
	}


	def addSubModel(Model parentModel,Model subModel){
		subModel.parentModel = parentModel
		parentModel.addToSubModels(subModel)
		subModel.save(failOnError: true)
		parentModel.save(failOnError: true)
	}


	def getTopLevelModels(){
		Model.findAllByParentModelIsNull()
	}
}
