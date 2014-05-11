package uk.co.mcv.model

class ConceptualDomain   {

	String name
	String description
	String catalogueId
	String catalogueVersion

	static hasMany = [valueDomains: ValueDomain, models:Model]

	static constraints = {
		name blank: false
		description nullable: true
		catalogueId nullable:true
		catalogueVersion nullable:true
	}

	static mapping = {
		description type: 'text'
	}
}
