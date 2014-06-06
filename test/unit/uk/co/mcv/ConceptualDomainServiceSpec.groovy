package uk.co.mcv

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll
import uk.co.mcv.model.ConceptualDomain

/**
 * Created by soheil on 06/05/2014.
 */
@TestFor(ConceptualDomainService)
@Mock(ConceptualDomain)
class ConceptualDomainServiceSpec extends Specification{

	def setup(){
		(1..25).each { index ->
			new ConceptualDomain(name:"CD${index}", description: "conceptual domain${index}",catalogueId: "1",catalogueVersion: "1").save(flush: true,failOnError: true)
		}
	}


	@Unroll
	def "Check if listAll returns all ConceptualDomains for max:#max, offset:#offset, resultCount:#resultCount "(){

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
     	 100   |   0      |	11			|	[name:"CD1"]
     	 100   |   0      |	1			|	[name:"CD1", description:"conceptual domain11" ]
     	  5    |   21     |	4			|	[]
	}


	@Unroll
	def "Check if countAll returns count for criteria:#criteria "(){

		when:"countAll is called"
		def filterCriteria = [:]
		//add filter criteria as items into filterCriteria like
		//params["name"]="CD"
		filters.each{ key , value ->
			filterCriteria.put(key,value)
		}

		def result = service.countAll(filterCriteria)

		then:"it returns the number of ConceptualDomains"
		result == resultCount

		where:""
		 resultCount	| filters
			11			|	[name:"CD1"]
			1			|	[name:"CD1", description:"conceptual domain11" ]
			25			|	[]
	}
}
