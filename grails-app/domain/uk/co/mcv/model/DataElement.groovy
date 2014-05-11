package uk.co.mcv.model

class DataElement  {

	String name
	String description
	String definition
	String catalogueVersion
	String catalogueId
	Map extensions

    static hasMany = [ subElements: DataElement,dataElementValueDomains: DataElementValueDomain]

	static belongsTo = [parent: DataElement,model:Model]

    static constraints = {
		model nullable: false
		parent nullable: true

		definition nullable: true
		description nullable:true
		name blank: false
		extensions nullable: true

		catalogueId nullable:false
		catalogueVersion nullable:true
    }
	
	static mapping = {
		description nullable: true, type: 'text'
		description nullable: true, type: 'text'

	}

}
