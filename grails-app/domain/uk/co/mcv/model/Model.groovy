package uk.co.mcv.model

class Model   {
	
	String name;
	String description;
	String version


	static hasMany = [dataElements: DataElement, valueDomains: ValueDomain]
			
    static constraints = {
        name nullable:false
        description nullable: true
    }
}
