package uk.co.mcv.api.v1

import grails.converters.JSON
import org.hibernate.criterion.CriteriaSpecification
import uk.co.mcv.BetterRestfulController
import uk.co.mcv.model.ConceptualDomain
import uk.co.mcv.model.DataElement
import uk.co.mcv.model.Model

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

class DataElementController extends BetterRestfulController{
    static namespace ="v1"

	def dataElementService

    DataElementController() {
        super(DataElement,true)
    }

	/**
	 * Return a simple object with metadata about the list
	 * @return
	 */
	def index(Integer max, Integer offset) {
		params.sort  = "name"
		params.order = "asc"
		super.index(max,offset)
	}


	@Override
	protected  List<ConceptualDomain> listAllResources(Map params) {
		dataElementService.listAll(params)

	}

	@Override
	protected Integer countResources(params) {
		dataElementService.countAll(params)
	}

//	@Override
//	protected  List<DataElement> listAllResources(Map params) {
//
//		//get filters parameter which is a JSON like {"name":"Mo","Description":"A simple"}
//		def filters = []
//
//		if(params["filters"])
//			filters = JSON.parse(params.filters);
//
//		Model  parentModel = null
//		if(params["ModelId"]){
//				parentModel = Model.get(params?.ModelId)
//				//can not find the Model, so returns no dataElement
//				if(parentModel == null)
//					return  []
//		}
//
//		resource.findAll(params,{
//			if(filters.size()>0)
//			{
//				filters.each{
//					key, value ->
//						ilike(key,"%"+value+"%")
//				}
//			}
//
//			if(parentModel){
//				model{
//					eq("id",parentModel.id)
//				}			}
//
//		})
//	}
//
//	@Override
//	protected Integer countResources(params) {
//		//get filters parameter which is a JSON like {"name":"Mo","Description":"A simple"}
//		def filters = []
//
//		if(params["filters"])
//			filters = JSON.parse(params.filters);
//
//
//		Model  parentModel = null
//		if(params["ModelId"]){
//			parentModel = Model.get(params?.ModelId)
//			//can not find the Model, so returns no dataElement
//			if(parentModel == null)
//				return  0
//		}
//
//		def criteria = DataElement.createCriteria()
//		criteria.count({
//			if(filters.size()>0)
//			{
//				filters.each{
//					key, value ->
//						ilike(key,"%"+value+"%")
//				}
//			}
//
//			if(parentModel){
//				model{
//					eq("id",parentModel.id)
//				}
//			}
//		})
//	}
}