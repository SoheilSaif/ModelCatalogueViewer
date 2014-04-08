package uk.co.mcv.model

class DataType  {
	
	String name
	Boolean enumerated
	Map enumerations


    static constraints = {
		enumerations nullable: true
		name blank: false
    }
	
	static hasMany = [valueDomains: ValueDomain]
}
