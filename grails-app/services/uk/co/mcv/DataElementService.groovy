package uk.co.mcv

import grails.transaction.Transactional
import uk.co.mcv.model.ConceptualDomain
import uk.co.mcv.model.DataElement

@Transactional
class DataElementService {


	def listAll(Map params) {
		def dataElements

		//make sure the ModelId is a long value
		if(params.containsKey("ModelId") && !(params["ModelId"] as String).isLong() )
			return  []


		if(!params) {
			dataElements = DataElement.list()
		} else {
			def cdProperties = DataElement.metaClass.properties*.name
			def criteria = DataElement.createCriteria()

			dataElements = criteria.list(params){
				params.each { field, value ->
					if (cdProperties.grep(field)) {
						ilike(field, "%"+value+"%")
					}
				}
				if(params["ModelId"]){
					model{
						eq("id",params["ModelId"] as long)
					}
				}
			}
		}
		return  dataElements
	}

	def countAll(Map filterCriteria) {
		def dataElements

		//make sure the ModelId is a long value
		if(filterCriteria.containsKey("ModelId") && !(filterCriteria["ModelId"] as String).isLong() )
			return  0

		if(!filterCriteria) {
			dataElements = DataElement.count()
		} else {

			//it may contain pagination parameters,so remove them
			Map parameters = filterCriteria.clone()
			parameters.remove("max")
			parameters.remove("offset")

			def cdProperties = DataElement.metaClass.properties*.name
			def criteria = DataElement.createCriteria()

			dataElements = criteria.count {
				parameters.each { field, value ->
					if (cdProperties.grep(field)) {
						ilike(field, "%"+value+"%")
					}
				}
				if(filterCriteria["ModelId"]){
					model{
						eq("id",filterCriteria["ModelId"] as long)
					}
				}
			}
		}
		return  dataElements
	}

}
