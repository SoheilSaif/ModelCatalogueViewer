package uk.co.mcv.model

class DataType  {
	
	String name
	Boolean enumerated
	String catalogueVersion
	String catalogueId
	Map enumerations

	static hasMany = [valueDomains: ValueDomain]

	static constraints = {
		enumerations nullable: true
		name blank: false
		catalogueId nullable:false
    }

}
