package uk.co.mcv.model

class D {

	String name
	static hasMany = [ds:D]
	static  belongsTo = [dParent:D]
	static constraints = {
	}
}
