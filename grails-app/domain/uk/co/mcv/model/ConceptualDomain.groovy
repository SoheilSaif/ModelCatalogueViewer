package uk.co.mcv.model

class ConceptualDomain   {

	String name
	String description
	String catalogueId
	String catalogueVersion

	static hasMany = [valueDomains: ValueDomain, models:Model]

	static constraints = {
		name blank: false
	}

	static mapping = {
		description type: 'text', nullable: true
		catalogueId nullable:false
	}
}
