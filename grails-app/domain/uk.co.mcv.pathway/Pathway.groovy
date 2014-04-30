package uk.co.mcv.pathway

import uk.co.mcv.model.DataElement

/**
 * A pathway object. Pathways contain <b>nodes</b>, which are connected by <b>links</b>.
 */
class Pathway {
	
	String  name
	String  userVersion = "1"
	Boolean isDraft = true
	String  description
	
	static hasMany = [
            nodes: Node,
            links: Link,

            // FIXME this should be injected using an AST transformation, or through extension.
            dataElements: DataElement
    ]
    static mappedBy = [
            nodes: 'pathway',
            links: 'pathway'
    ]

    static constraints = {
        description     nullable: true
		nodes           nullable: true
        links           nullable: true
        dataElements    nullable: true
    }

    static mapping = {
        nodes cascade: "all-delete-orphan"
        links cascade: "all-delete-orphan"
    }
}
