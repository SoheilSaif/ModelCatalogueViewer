package uk.co.mcv

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll
import uk.co.mcv.model.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(DataElementService)
@Mock([ConceptualDomain,Model,DataElement,ValueDomain,DataType,MeasurementUnit])
class DataElementServiceSpec extends Specification {

	def dataElementValueDomainService

	def setup(){

		dataElementValueDomainService = Mock(DataElementValueDomainService)

		def conDomain = new ConceptualDomain(name:"NHIC", description: "NHIC conceptual domain", catalogueId: "1", catalogueVersion: "1").save(flush:true,failOnError: true)

		def model1 = new Model(name:"Model1", catalogueId:"Model1", catalogueVersion:"v1")
		conDomain.addToModels(model1)
		model1.save(flush:true, failOnError: true)

		def model2 = new Model(name:"Model2", catalogueId:"Model2", catalogueVersion:"v1")
		conDomain.addToModels(model2)
		model2.save(flush:true, failOnError: true)


		def measurementUnit  = new MeasurementUnit(name: "centimeter",symbol: "cm").save(flush: true)
		def dataType = new DataType(enumerated: false, name: "d",catalogueId: "d", catalogueVersion: "d").save(flush:true,failOnError: true)
		def valueDomain = new ValueDomain(name: "d", catalogueId: "d", catalogueVersion: "d")
		dataType.addToValueDomains(valueDomain)
		measurementUnit.addToValueDomains(valueDomain)
		conDomain.addToValueDomains(valueDomain)
		valueDomain.save(flush:true, failOnError: true)

		(1..5).each {index->
			def dataElement = new DataElement(name:"DE${index}", description:"this is dataelement${index}" ,catalogueId: "d", catalogueVersion: "d")
			model1.addToDataElements(dataElement)
			dataElement.save(flush:true,failOnError: true)
			dataElementValueDomainService.link(dataElement,valueDomain)

		}

		(1..20).each {index->
			def dataElement = new DataElement(name:"DE${index}", catalogueId: "d", catalogueVersion: "d")
			model2.addToDataElements(dataElement)
			dataElement.save(flush:true,failOnError: true)
			dataElementValueDomainService.link(dataElement,valueDomain)
		}
	}

	@Unroll
	def "Check if listAll returns all DataElements for max:#max, offset:#offset, resultCount:#resultCount "(){

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

		then:"it returns all the DataElements"
		result.size() == resultCount

		where:""
		max   | offset   | resultCount	| criteria
		100   |   0      |	12			|	[name:"DE1"]
		10    |   21     |	4			|	[name:"DE"]
		100   |   0      |	25			|	[]
	}


	@Unroll
	def "Check if listAll returns all DataElements for max:#max, offset:#offset, resultCount:#resultCount with modelId:#modelId"(){

		when:"listAll is called"
		def params = [:]
		params["max"] = max
		params["offset"] = offset

		//add filter criteria as items into params like
		//params["name"]="CD"
		criteria.each{ key , value ->
			params.put(key,value)
		}

		if(modelId)
			params.put("ModelId",modelId)

		def result = service.listAll(params)

		then:"it returns all the DataElements"
		result.size() == resultCount

		where:""
		max   | offset   | resultCount	| criteria			|	modelId
		100   |   0      |	1			|	[name:"DE1"]	|	1
		100   |   0	     |	25			|	[name:"DE"]		|	null
		100   |   0      |	5			|	[]				|	1
		100   |   0      |	20			|	[]				|	2
		100   |   0      |	2			|	[name:"DE2"]	|	2
		100   |   0      |	1			|	[name:"DE20"]	|	2
		100   |   0      |	0			|	[]				|	"InvalidInput"
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

		then:"it returns the number of DataElements"
		result == resultCount

		where:""
		resultCount	| filters
		12			|	[name:"DE1"]
		1			|	[name:"DE1", description:"this is dataelement1" ]
		25			|	[]
	}


	@Unroll
	def "Check if countAll returns count for criteria:#filters with modelId:#modelId"(){

		when:"countAll is called"
		def filterCriteria = [:]
		//add filter criteria as items into filterCriteria like
		//params["name"]="CD"
		filters.each{ key , value ->
			filterCriteria.put(key,value)
		}

		if(modelId)
			filterCriteria.put("ModelId",modelId)

		def result = service.countAll(filterCriteria)

		then:"it returns the number of DataElements"
		result == resultCount

		where:""
		resultCount	| filters												| modelId
		1			|	[name:"DE1"]										|	1
		11			|	[name:"DE1"]										|	2
		1			|	[name:"DE1", description:"this is dataelement1" ]	|	null
		5			|	[]													|	1
		20			|	[]													|	2
		0			|	[]													|	"InvalidInput"

	}

}
