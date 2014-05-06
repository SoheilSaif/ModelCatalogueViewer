package uk.co.mcv.model

class B {
	String name

	static  belongsTo = [a:A]
	static hasMany = [cs:C]
	static constraints = {
	}
}
