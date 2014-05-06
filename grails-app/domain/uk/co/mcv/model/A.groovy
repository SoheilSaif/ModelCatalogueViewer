package uk.co.mcv.model

class A {
	String name
	Set bs =[]
	static hasMany = [bs:B]

	static constraints = {
	}
}
