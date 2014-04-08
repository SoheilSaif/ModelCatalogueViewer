package uk.co.mcv.model

class Model   {
	
	Integer conceptId;
	String name;
	String description;

	static hasMany = [dataElements: DataElement, valueDomains: ValueDomain]
			
    static constraints = {
    }
}
