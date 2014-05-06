package uk.co.mcv.model


//Location does not have any relation to Author
//so it does not have belongsTo, so delete cascade does not apply
//so it does not have belongsTo, so it is a uni-directional relation from Author->Location
class Location {

	String name

	static constraints = {
	}
}
