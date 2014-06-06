package uk.co.mcv

import grails.converters.JSON
import grails.rest.RestfulController

/**
 * A RestfulController with better support for paginated list results.
 *
 * The default Grails RestfulController (at least at 2.3.5) just returns a list of values for `index`.
 * When using this in an AJAX call you often need to specify `max` and `offset` values to paginate the data,
 * so this extension provides that information back, as well as the overall count.
 *
 * Created by rb on 05/03/2014.
 *
 * It's also extended to support filter to filter over fields
 * Added by Soheil on 11/04/2014
 */
class BetterRestfulController<T> extends RestfulController<T>{

	BetterRestfulController(Class<T> resource) {
		this(resource, false)
	}

    BetterRestfulController(Class<T> resource, boolean readOnly) {
		super(resource, readOnly)

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
    protected  List<T> listAllResources(Map params) {

        //get filters parameter which is a JSON like {"name":"Mo","Description":"A simple"}
        def filters = []

        if(params["filters"])
           filters = JSON.parse(params.filters);

        resource.findAll(params,{
                    if(filters.size()>0)
                    {
                        filters.each{
                            key, value ->
                                ilike(key,"%"+value+"%")
                        }
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

        def criteria = resource.createCriteria()
        criteria.count({
            if(filters.size()>0)
            {
                filters.each{
                    key, value ->
                        ilike(key,"%"+value+"%")
                }
            }
        })
    }
}