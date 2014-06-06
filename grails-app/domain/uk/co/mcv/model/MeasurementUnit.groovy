package uk.co.mcv.model

class MeasurementUnit {

	String name
	String symbol

	static hasMany = [valueDomains: ValueDomain]

	static constraints = {
		name nullable: false
		symbol nullable: false
	}
}
