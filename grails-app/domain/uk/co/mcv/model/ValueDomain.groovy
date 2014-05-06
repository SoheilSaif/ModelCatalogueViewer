package uk.co.mcv.model


class ValueDomain  {

    String name
    String unitOfMeasure
    String regexDef
    String format
    String description
	String catalogueVersion
	String catalogueId

	DataType dataType


	static hasMany = [dataElements: DataElement]

    static belongsTo = [conceptualDomain: ConceptualDomain]

    static constraints = {
        conceptualDomain nullable:false
        dataType nullable:false
        description nullable:true, type: 'text'

		unitOfMeasure nullable:true
        regexDef nullable:true
        name blank: false
        format nullable:true
    }

    static mapping = {
        description type: 'text'
    }
}
