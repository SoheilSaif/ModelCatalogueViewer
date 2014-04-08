package uk.co.mcv.model


class ValueDomain  {

    String name
    String unitOfMeasure
    String regexDef
    String format
    String description
    DataType dataType
    Set dataElementValueDomains = []
    ConceptualDomain conceptualDomain


    static hasMany = [dataElementValueDomains: DataElementValueDomain]

    static belongsTo = [conceptualDomain: ConceptualDomain]

    static constraints = {
        conceptualDomain nullable:true
        dataType nullable:true
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
