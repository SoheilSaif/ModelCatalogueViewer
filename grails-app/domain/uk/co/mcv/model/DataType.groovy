package uk.co.mcv.model

class DataType  {
	
	String name
	Boolean enumerated
	String catalogueVersion
	String catalogueId
	Map enumerations

	Set valueDomains = []

	static hasMany = [valueDomains: ValueDomain]

	static constraints = {
		enumerations nullable: true
		name blank: false

    }
	


}
