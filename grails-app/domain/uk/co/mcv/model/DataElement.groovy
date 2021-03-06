package uk.co.mcv.model

class DataElement  {

	String name
	String description
	String definition
	String catalogueVersion
	String catalogueId

	ValueDomain valueDomain


    static hasMany = [ subElements: DataElement]
	static belongsTo = [parent: DataElement,model:Model]

    static constraints = {
		valueDomain nullable: false
		model nullable: false
		parent nullable: true

		definition nullable: true
		description nullable:true
		name blank: false
    }
	
	static mapping = {
		description nullable: true, type: 'text'
		description nullable: true, type: 'text'
		catalogueId nullable:false
	}
	

	static final String HELP = "A data element describes a logical unit of data that has precise meaning or precise semantics." +
	"Alternatively one can understand a data element as something close to a column in a database table.  <br/>" +
	"i.e. Address - City <br/>" +
	"<br/>" +
	"It is important to include a clear data element <strong>definition</strong><br/>" +
	"A good definition is:<br/>" +
	"Precise - The definition should use words that have a precise meaning. Try to avoid words that have multiple meanings or multiple word senses.<br/>" +
	"Concise - The definition should use the shortest description possible that is still clear.<br/>" +
	"Non Circular - The definition should not use the term you are trying to define in the definition itself. This is known as a circular definition.<br/>" +
	"Distinct - The definition should differentiate a data element from other data elements. This process is called disambiguation.<br/>" +
	"Unencumbered - The definition should be free of embedded rationale, functional usage, domain information, or procedural information.<br/>" +
	"<br/><br/>" +
	"An example of a bad data element definition would be:" +
	"City - the name of the city that forms a part of an address" +
	"An example of a better definition would be:" +
	"A component of an address that specifies a location by identification of an urban area.<br/>" +
	"<br/>" +
	"A data element can have a number of different <strong>value domains</strong><br/>" +
	"A value domain expresses the set of allowed values for a data element."

	
}
