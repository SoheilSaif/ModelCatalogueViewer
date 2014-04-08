package model

class Model   {
	
	Integer conceptId;
	String description;
	
	static hasMany = [dataElements: DataElement, valueDomains: ValueDomain]
			
    static constraints = {
    }
}
