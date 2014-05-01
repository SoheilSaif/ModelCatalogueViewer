package uk.co.mcv.model

class Model   {
	
	String name;
	String description;
	String catalogueVersion
	String catalogueId

	static belongsTo = [conceptualDomain: ConceptualDomain,parentModel:Model]

	static hasMany = [dataElements: DataElement, subModels:Model]

	static mapping = {
		dataElements cascade: 'all-delete-orphan'
		subModels cascade: 'all-delete-orphan'
	}

    static constraints = {
		conceptualDomain nullable: false
        name nullable:false
        description nullable: true
		parentModel nullable: true
    }
}