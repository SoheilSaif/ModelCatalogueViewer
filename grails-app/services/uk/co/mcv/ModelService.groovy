package uk.co.mcv

import grails.transaction.Transactional
import uk.co.mcv.model.ConceptualDomain
import uk.co.mcv.model.Model

@Transactional
class ModelService {

	/**
	 * A topLevel model is a model which its parent is Null
	 * @return
	 */
	def getTopLevelModels(ConceptualDomain conceptualDomain){
		Model.findAllByConceptualDomainAndParentModelIsNull(conceptualDomain)
	}


	def listAll(Map params) {
		def models

		//make sure the ConceptualDomainId is a long value
		if(params.containsKey("ConceptualDomainId") && !(params["ConceptualDomainId"] as String).isLong() )
			return  []

		if(!params) {
			models = Model.list()
		} else {
			def cdProperties = Model.metaClass.properties*.name
			def criteria = Model.createCriteria()

			models = criteria.list(params){
				params.each { field, value ->
					if (cdProperties.grep(field)) {
						ilike(field, "%"+value+"%")
					}
				}
				if(params.containsKey("ConceptualDomainId")){
					conceptualDomain{
						eq("id",params["ConceptualDomainId"] as long)
					}
				}
			}
		}
		return  models
	}

	def countAll(Map filterCriteria) {
		def modelCount


		//make sure the ConceptualDomainId is a long value
		if(filterCriteria.containsKey("ConceptualDomainId") && !(filterCriteria["ConceptualDomainId"] as String).isLong() )
			return  0


		if(!filterCriteria) {
			modelCount = Model.count()
		} else {

			//it may contain pagination parameters,so remove them
			Map parameters = filterCriteria.clone()
			parameters.remove("max")
			parameters.remove("offset")

			def cdProperties = Model.metaClass.properties*.name
			def criteria = Model.createCriteria()

			modelCount = criteria.count {
				parameters.each { field, value ->
					if (cdProperties.grep(field)) {
						ilike(field, "%"+value+"%")
					}
				}
				if(filterCriteria["ConceptualDomainId"]){
						conceptualDomain{
							eq("id",filterCriteria["ConceptualDomainId"] as long)
						}
				}
			}
		}
		return  modelCount
	}
}