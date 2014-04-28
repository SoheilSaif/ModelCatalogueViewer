package uk.co.mcv.model

class ConceptualDomain   {

	String name
	String description
	String version

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
