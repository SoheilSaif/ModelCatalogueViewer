package uk.co.mcv.model


//It is belongsTo CLASS_NAME
//belongsTo means apply delete cascade
//using CLASS_NAME means it is a uni-directional relation
//if it were a belongsTo = [authorBelongs: AuthorBelong] then it was a bi-directional relation
class LocationBelong {

	String name
	static  belongsTo = AuthorBelong
	static constraints = {
	}
}
