package uk.co.mcv.model

class ConceptualDomain   {

	String name
	String description
	String catalogueId
	String catalogueVersion


	static hasMany = [valueDomains: ValueDomain, models:Model]

	static constraints = {
		valueDomains nullable:true
		name blank: false
	}

	static mapping = {
		description type: 'text'
		valueDomains cascade: 'save-update'
	}
	static class DataElementLocal {

		String name
		String code

		static constraints = {
		}
	}
}
