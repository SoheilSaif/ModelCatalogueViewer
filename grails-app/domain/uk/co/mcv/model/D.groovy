package uk.co.mcv.model


//TEST FOR PARENT-CHILD RELATIONS
class D {

	String name
	static hasMany = [ds:D]
	static  belongsTo = [dParent:D]
	static constraints = {
	}
}
