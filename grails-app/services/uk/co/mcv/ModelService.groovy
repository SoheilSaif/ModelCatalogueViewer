package uk.co.mcv

import grails.transaction.Transactional
import uk.co.mcv.model.Model

@Transactional
class ModelService {

	/**
	 * Adds a subModel to a parentModel
	 * @param parentModel
	 * @param subModel
	 * @return
	 */
    def addSubModel(Model parentModel,Model subModel){
		parentModel.addToSubModels(subModel)
		parentModel.save(flush: true)
	}

	/**
	 * A topLevel model is a model which its parent is Null
	 * @return
	 */
	def getTopLevelModels(){
		Model.findAllByParentModelIsNull()
	}

}
