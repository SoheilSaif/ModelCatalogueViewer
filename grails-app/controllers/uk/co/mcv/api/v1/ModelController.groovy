package uk.co.mcv.api.v1

import grails.converters.JSON
import uk.co.mcv.BetterRestfulController
import uk.co.mcv.model.ConceptualDomain
import uk.co.mcv.model.DataElement
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


	/**
	 * Return a simple object with metadata about the list
	 * @return
	 */
	def index(Integer max, Integer offset){
		params.sort  = "name"
		params.order = "asc"
		super.index(max,offset)
	}

	@Override
	protected  List<Model> listAllResources(Map params) {
		modelService.listAll(params)

	}

	@Override
	protected Integer countResources(params) {
		modelService.countAll(params)
	}

}

