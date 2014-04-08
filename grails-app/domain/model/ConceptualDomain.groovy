package model

class ConceptualDomain   {

	String name
	String description

	static hasMany = [valueDomains: ValueDomain]

	static constraints = {
		valueDomains nullable:true
		name blank: false
	}

	def prepareForDelete(){
		if(this.valueDomains.size()!=0){
			this.valueDomains.each{ p->
				p.prepareForDelete()
			}
		}
	}

	static mapping = {
		description type: 'text'
		valueDomains cascade: 'save-update'
	}
}
