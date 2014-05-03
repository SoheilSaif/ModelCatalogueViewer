package uk.co.mcv.api.v1

import grails.converters.JSON
import uk.co.mcv.BetterRestfulController
import uk.co.mcv.model.Model


/**
 * A readonly controller to get list of all available Models
 *
 */

class ModelController extends  BetterRestfulController{
    static namespace = "v1"

	def modelService

    ModelController(){
        super(Model,true)
    }


	def index(Integer max, Integer offset){

		//topLevels params provided, so returns all top level models without considering max,offset params
		if(params.boolean("topLevels")){
			def topLevels = modelService.getTopLevelModels()
			def returnValue = [
					objects: topLevels,
					max:topLevels.size(),
					offset: 0,
					total: topLevels.size()
			]
			respond returnValue as Object, model: [("${resourceName}Count".toString()): topLevels.size()]
			return
		}

		//otherwise call index of super for considering max and offset
		super.index(max,offset)
	}
}