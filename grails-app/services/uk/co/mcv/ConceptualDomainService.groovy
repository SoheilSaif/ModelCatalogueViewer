package uk.co.mcv

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

}
