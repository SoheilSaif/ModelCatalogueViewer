package uk.co.mcv

import grails.transaction.Transactional
import uk.co.mcv.model.Model

@Transactional
class ModelService {

    def deleteModel(Model model) {

		//remove dataElements from Model
		model.dataElements?.collect().each { de ->
			model.removeFromDataElements(de)
			de.delete()
		}
		//delete sub Models if any exists
		//call deleteModel method recursively
		model.subModels?.collect().each { subModel ->
			deleteModel(subModel)
		}
		//remove model
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
