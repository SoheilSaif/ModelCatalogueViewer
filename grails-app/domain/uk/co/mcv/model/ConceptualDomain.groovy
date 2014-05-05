package uk.co.mcv.model

class ConceptualDomain   {

	String name
	String description
	String catalogueId
	String catalogueVersion

	Set valueDomains = []
	Set models = []

	static hasMany = [valueDomains: ValueDomain, models:Model]

	static constraints = {
		name blank: false
	}

	static mapping = {
		description type: 'text'
	}
}
