package uk.co.mcv.model

class DataType  {
	
	String name
	String catalogueVersion
	String catalogueId
//	Boolean enumerated
//	Map enumerations

	static hasMany = [valueDomains: ValueDomain]

	static constraints = {
//		enumerations nullable: true
		name blank: false
		catalogueId nullable:true
		catalogueVersion nullable: true
    }

	private final static defaultDataTypeDefinitions = [
			[name: "String", description: "java.lang.String"],
			[name: "Integer", description: "java.lang.Integer"],
			[name: "Double", description: "java.lang.Double"],
			[name: "Boolean", description: "java.lang.Boolean"],
			[name: "Date", description: "java.util.Date"],
			[name: "Time", description: "java.sql.Time"],
			[name: "Currency", description: "java.util.Currency"]
	]

	static initDefaultDataTypes() {
		for (definition in defaultDataTypeDefinitions) {
			DataType existing = DataType.findByName(definition.name)
			if (!existing) {
				new DataType(definition).save()
			}
		}
	}
}