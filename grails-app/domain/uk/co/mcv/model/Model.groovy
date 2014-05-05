package uk.co.mcv.model

class Model   {
	
	String name;
	String description;
	String catalogueVersion
	String catalogueId

	ConceptualDomain conceptualDomain
	Model parentModel
	Set dataElements = []
	Set subModels = []

	static hasMany = [dataElements: DataElement, subModels:Model]
	static belongsTo = [conceptualDomain: ConceptualDomain,parentModel:Model]

    static constraints = {
		conceptualDomain nullable: false
		parentModel nullable: true

		name nullable:false
        description nullable: true
    }
}