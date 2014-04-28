package uk.co.mcv.model

class DataType  {
	
	String name
	Boolean enumerated
	Map enumerations
	String catalogueVersion
	String catalogueId



	static auditable = true

    static searchable = {
        content: spellCheck 'include'
    }

    static constraints = {
		enumerations nullable: true
		name blank: false
    }
	
	static hasMany = [valueDomains: ValueDomain]
}
