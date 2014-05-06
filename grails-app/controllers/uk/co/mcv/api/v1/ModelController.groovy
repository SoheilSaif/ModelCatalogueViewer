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

	/*
	* Returns a list of objects,in a filtered and paged format
	* @return
	*/
	@Override
	protected  List<Model> listAllResources(Map params) {

		//get filters parameter which is a JSON like {"name":"Mo","Description":"A simple"}
		def filters = []

		println params

		if(params["filters"])
			filters = JSON.parse(params.filters);

		ConceptualDomain  parentCD = null
		if(params["ConceptualDomainId"]){
			parentCD = ConceptualDomain.get(params?.ConceptualDomainId)
			//can not find the ConceptualDomain, so returns empty list
			if(parentCD == null)
				return  []
		}

		resource.findAll(params,{
			if(filters.size()>0)
			{
				filters.each{
					key, value ->
						ilike(key,"%"+value+"%")
				}
			}

			if(parentCD){
				conceptualDomain{
					eq("id",parentCD.id)
				}			}

		})
	}

	//Extended to support filters
	@Override
	protected Integer countResources(params) {
		//get filters parameter which is a JSON like {"name":"Mo","Description":"A simple"}
		def filters = []

		if(params["filters"])
			filters = JSON.parse(params.filters);


		ConceptualDomain  parentCD = null
		if(params["ConceptualDomainId"]){
			parentCD = ConceptualDomain.get(params?.ConceptualDomainId)
			//can not find the ConceptualDomain, so returns empty list
			if(parentCD == null)
				return  0
		}

		def criteria = Model.createCriteria()
		criteria.count({
			if(filters.size()>0)
			{
				filters.each{
					key, value ->
						ilike(key,"%"+value+"%")
				}
			}

			if(parentCD){
				conceptualDomain{
					eq("id",parentCD.id)
				}
			}
		})
	}

}

