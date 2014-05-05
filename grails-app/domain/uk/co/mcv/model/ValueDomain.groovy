package uk.co.mcv.model


class ValueDomain  {

    String name
    String unitOfMeasure
    String regexDef
    String format
    String description
	String catalogueVersion
	String catalogueId

	Set dataElements = []
	DataType dataType
	ConceptualDomain conceptualDomain


	static hasMany = [dataElements: DataElement]

    static belongsTo = [conceptualDomain: ConceptualDomain]

    static constraints = {
        conceptualDomain nullable:false
        dataType nullable:false

        description nullable:true
        unitOfMeasure nullable:true
        regexDef nullable:true
        name blank: false
        format nullable:true
    }

    static mapping = {
        description type: 'text'
    }
}
