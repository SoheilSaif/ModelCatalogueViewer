package uk.co.mcv.model

class Model   {
	
	String name;
	String description;
	String catalogueVersion
	String catalogueId

	static belongsTo = [conceptualDomain: ConceptualDomain]

	static hasMany = [dataElements: DataElement]


	static mapping = {
		dataElements cascade: 'all-delete-orphan'
	}

    static constraints = {
		conceptualDomain nullable: false
        name nullable:false
        description nullable: true
    }
}
