package uk.co.mcv.api.v1

import grails.converters.JSON
import org.hibernate.criterion.CriteriaSpecification
import uk.co.mcv.BetterRestfulController
import uk.co.mcv.model.DataElement
import uk.co.mcv.model.Model

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

class DataElementController extends BetterRestfulController{
    static namespace ="v1"

    DataElementController()
    {
        super(DataElement,true)
    }

	/**
	 * Return a simple object with metadata about the list
	 * @return
	 */
	def index(Integer max, Integer offset){
		params.max = Math.min(max ?: 10, 100)
		params.offset = offset ?: 0

		def returnValue = [
				objects: listAllResources(params),
				max: params.max,
				offset: params.offset,
				total: countResources(params)
		]
		respond returnValue as Object, model: [("${resourceName}Count".toString()): countResources(params)]
	}



	/*
	* Returns a list of objects,in a filtered and paged format
	* @return
	*/
	@Override
	protected  List<DataElement> listAllResources(Map params) {

		//get filters parameter which is a JSON like {"name":"Mo","Description":"A simple"}
		def filters = []

		if(params["filters"])
			filters = JSON.parse(params.filters);

		Model  parentModel = null
		if(params["ModelId"]){
				parentModel = Model.get(params?.ModelId)
				//can not find the Model, so returns no dataElement
				if(parentModel == null)
					return  []
			}

		DataElement.findAll(params,{

			if(filters.size()>0){
				filters.each{
					key, value ->
						ilike(key,"%"+value+"%")
				}
			}
			if(parentModel){
						model.id == parentModel.id
			}

	})

}

	//Extended to support filters
	@Override
	protected Integer countResources(params) {
		//get filters parameter which is a JSON like {"name":"Mo","Description":"A simple"}
		def filters = []

		if(params["filters"])
			filters = JSON.parse(params.filters);

		Model  parentModel = null
		if(params["ModelId"]){
			parentModel = Model.get(params?.ModelId)
			//can not find the Model, so returns no dataElement
			if(parentModel == null)
				return  0
		}


		def criteria = DataElement.createCriteria()
		criteria.count({
			if(filters.size()>0)
			{
				filters.each{
					key, value ->
						ilike(key,"%"+value+"%")
				}
			}
			if(parentModel){
				model{
					eq("id",parentModel.id)
				}
			}
		})
	}
}