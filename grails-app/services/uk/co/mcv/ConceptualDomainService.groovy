package uk.co.mcv

import grails.converters.JSON
import grails.transaction.Transactional
import uk.co.mcv.model.ConceptualDomain
import uk.co.mcv.model.ValueDomain

@Transactional
class ConceptualDomainService {
	/**
	 * To delete a conceptualDomain
	 * @param conceptualDomain
	 */
	def delete(ConceptualDomain conceptualDomain) {
		//because of relationships and belongsTo relations
		//when we delete a conceptualDomain, all its models, subModels, dataElements and ValueDomains will be deleted
		conceptualDomain.delete()
	}

	def listAll(Map params) {
		def cDomains

		if(!params) {
			cDomains = ConceptualDomain.list()
		} else {
			def cdProperties = ConceptualDomain.metaClass.properties*.name
			def criteria = ConceptualDomain.createCriteria()

			cDomains = criteria.list(params){
					params.each { field, value ->
						if (cdProperties.grep(field)) {
							ilike(field, "%"+value+"%")
						}
					}
			}
		}
		return  cDomains
	}


	def countAll(Map filterCriteria) {
		def cDomains

		if(!filterCriteria) {
			cDomains = ConceptualDomain.count()
		} else {

			//it may contain pagination parameters,so remove them
			Map parameters = filterCriteria.clone()
			parameters.remove("max")
			parameters.remove("offset")

			def cdProperties = ConceptualDomain.metaClass.properties*.name
			def criteria = ConceptualDomain.createCriteria()

			cDomains = criteria.count {
				parameters.each { field, value ->
					if (cdProperties.grep(field)) {
						ilike(field, "%"+value+"%")
					}
				}
			}
		}
		return  cDomains
	}

}
