package uk.co.mcv.model


class ValueDomain  {

    String name
    String regexDef
    String format
    String description
	String catalogueVersion
	String catalogueId

	DataType dataType
	MeasurementUnit measurementUnit

	Set dataElementValueDomains = []


	static hasMany = [dataElementValueDomains: DataElementValueDomain]

    static belongsTo = [conceptualDomain: ConceptualDomain]

    static constraints = {
        conceptualDomain nullable:false
        dataType nullable:false
        description nullable:true, type: 'text'

		measurementUnit nullable:true
        regexDef nullable:true
        name blank: false
        format nullable:true

		catalogueId nullable:true
		catalogueVersion nullable:true
    }

    static mapping = {
        description type: 'text'
    }
}
