package uk.co.mcv.model

//an Author has a locationBelong
//but in locationBelong, it is a belongsTo AuthorBelong
// belongsTo CLASS_NAME  uni-directional relation
// belongsTo (so delete cascade applies)
class AuthorBelong {

	String name
	LocationBelong locationBelong
	static constraints = {
	}
}
