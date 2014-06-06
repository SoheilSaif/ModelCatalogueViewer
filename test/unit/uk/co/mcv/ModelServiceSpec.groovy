package uk.co.mcv

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll
import uk.co.mcv.model.*

/**
 * Created by soheil on 06/05/2014.
 */
@TestFor(ModelService)
@Mock([ConceptualDomain,Model])
class ModelServiceSpec extends Specification{

	def setup(){
		ConceptualDomain cDomain1 = new ConceptualDomain(name:"CD1", description: "conceptual domain1",catalogueId: "1",catalogueVersion: "1").save(flush: true,failOnError: true)
		ConceptualDomain cDomain2 = new ConceptualDomain(name:"CD2", description: "conceptual domain2",catalogueId: "1",catalogueVersion: "1").save(flush: true,failOnError: true)

		//add 5 models to conceptualDomain1
		(1..5).each { index ->
			def model = new Model(name:"CD1-model${index}",description:"description ${index}",catalogueId: "1",catalogueVersion: "1" )
			cDomain1.addToModels(model)
			model.save(flush: true,failOnError: true)
		}

		//add 10 models to conceptualDomain2
		(1..10).each { index ->
			def model = new Model(name:"CD2-model${index}",description:"description ${index}",catalogueId: "1",catalogueVersion: "1")
			cDomain2.addToModels(model)
			model.save(flush: true,failOnError: true)
		}
	}


	@Unroll
	def "Check if listAll returns all Models for max:#max, offset:#offset, resultCount:#resultCount with ConceptualDomainId:#ConceptualDomainId"(){

		when:"listAll is called"
		def params = [:]
		params["max"] = max
		params["offset"] = offset

		//add filter criteria as items into params like
		//params["name"]="CD"
		criteria.each{ key , value ->
			params.put(key,value)
		}

		if(ConceptualDomainId)
			params.put("ConceptualDomainId",ConceptualDomainId)

		def result = service.listAll(params)

		then:"it returns all the ConceptualDomains"
		result.size() == resultCount

		where:""
		max   | offset   | resultCount	| criteria					|	   ConceptualDomainId
		100   |   0      |	1			|	[name:"CD1-model1"]		|			null
		100   |   0      |	5			|	[name:"CD"]				|			1
		100   |   0      |	10			|	[name:"CD"]				|			2
		100   |   0      |	1			|	[name:"CD1-model1"]		|			1
		100   |   0      |	10			|	[name:"CD2"]			|			2
		100   |   0      |	0			|	[]						|			"InvalidInput"
	}

	@Unroll
	def "Check if listAll returns all Models for max:#max, offset:#offset, resultCount:#resultCount "(){

		when:"listAll is called"
		def params = [:]
		params["max"] = max
		params["offset"] = offset

		//add filter criteria as items into params like
		//params["name"]="CD"
		criteria.each{ key , value ->
			params.put(key,value)
		}

		def result = service.listAll(params)

		then:"it returns all the ConceptualDomains"
		result.size() == resultCount

		where:""
		max   | offset   | resultCount	| criteria
		100   |   0      |	1			|	[name:"CD1-model1"]
		100   |   0      |	5			|	[name:"CD1", description:"description" ]
		30    |   0      |	15			|	[]
		5     |   11     |	4			|	[]
	}


	@Unroll
	def "Check if countAll returns count for criteria:#filters "(){

		when:"countAll is called"
		def filterCriteria = [:]
		//add filter criteria as items into filterCriteria like
		//params["name"]="CD"
		filters.each{ key , value ->
			filterCriteria.put(key,value)
		}

		def result = service.countAll(filterCriteria)

		then:"it returns the number of Models"
		result == resultCount

		where:""
		resultCount	| filters
		1			|	[name:"CD1-model1"]
		3			|	[name:"model1"]
		0			|	[name:"CD1", description:"XYZ" ]
		15			|	[]
	}


	@Unroll
	def "Check if countAll returns count for criteria:#filters with ConceptualDomainId:#ConceptualDomainId"(){

		when:"countAll is called"
		def filterCriteria = [:]
		//add filter criteria as items into filterCriteria like
		//params["name"]="CD"
		filters.each{ key , value ->
			filterCriteria.put(key,value)
		}

		if(ConceptualDomainId)
			filterCriteria.put("ConceptualDomainId",ConceptualDomainId)

		def result = service.countAll(filterCriteria)

		then:"it returns the number of Models"
		result == resultCount

		where:""
		resultCount	| filters								|	ConceptualDomainId
		1			|	[name:"CD1-model1"]					|	null
		1			|	[name:"model1"]						|	1
		0			|	[name:"CD1", description:"XYZ" ]	|	null
		15			|	[]									|	null
		5			|	[]									|	1
		10			|	[]									|	2
		2			|	[name:"model1"]						|	2
		0			|	[]									|	"InValidInput"
	}

}
