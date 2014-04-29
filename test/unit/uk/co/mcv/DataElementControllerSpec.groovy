package uk.co.mcv

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.spock.IntegrationSpec
import org.codehaus.groovy.grails.web.json.JSONArray
import org.spockframework.compiler.model.Spec
import org.springframework.web.context.support.WebApplicationContextUtils
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll
import uk.co.brc.mcv.marshaller.DataElementMarshaller
import uk.co.brc.mcv.marshaller.DataTypeMarshaller
import uk.co.brc.mcv.marshaller.ModelMarshaller
import uk.co.brc.mcv.marshaller.ValueDomainMarshaller
import uk.co.mcv.api.v1.DataElementController
import uk.co.mcv.api.v1.ModelController
import uk.co.mcv.model.ConceptualDomain
import uk.co.mcv.model.DataElement
import uk.co.mcv.model.DataType
import uk.co.mcv.model.Model
import uk.co.mcv.model.ValueDomain

/**
 * Created by soheil on 23/04/2014.
 */
@TestFor(DataElementController)
@Mock([Model,ConceptualDomain,DataElement,DataType,ValueDomain])
class DataElementControllerSpec extends  Specification{



	def setup(){


		def conDomain = new ConceptualDomain(name:"NHIC", description: "NHIC conceptual domain",catalogueId: "1",catalogueVersion: "1").save(failOnError: true)


		//Create 5 sample Models
		(1..5).each { index ->
			new Model(name:"Model${index}",catalogueId:"Model${index}",catalogueVersion:"v1",conceptualDomain: conDomain  ).save(flush: true)
		}

		//add some DataElements into Model[0]
		(1..5).each { index ->
			def dataType = new DataType(name:"DT${index}",version: "1",enumerated: false,catalogueId:"Model${index}",catalogueVersion:"v1").save(failOnError: true)
			def valueDomain = new ValueDomain(name:"VD${index}",version: "1",dataType: dataType,catalogueId:"Model${index}",catalogueVersion:"v1").save(failOnError: true)
			def dataElement = new DataElement(name:"DEM1-${index}",version: "1",description:"Desc${index}",valueDomain:valueDomain,catalogueId:"Model${index}",catalogueVersion:"v1" )
			Model.list()[0].addToDataElements(dataElement).save(failOnError: true)
		}


		//add some DataElements into Model[1]
		(1..3).each { index ->
			def dataType = new DataType(name:"DT${index}",version: "1",enumerated: false,catalogueId:"Model${index}",catalogueVersion:"v1").save(failOnError: true)
			def valueDomain = new ValueDomain(name:"VD${index}",version: "1",dataType: dataType,catalogueId:"Model${index}",catalogueVersion:"v1").save(failOnError: true)
			def dataElement = new DataElement(name:"DE${index}",version: "1",description:"Desc${index}",valueDomain:valueDomain ,catalogueId:"Model${index}",catalogueVersion:"v1")
			Model.list()[1].addToDataElements(dataElement).save(failOnError: true)
		}

		//add some DataElements into Model[2]
		(1..2).each { index ->
			def dataType = new DataType(name:"DT${index}",version: "1",enumerated: false,catalogueId:"Model${index}",catalogueVersion:"v1").save(failOnError: true)
			def valueDomain = new ValueDomain(name:"VD${index}",version: "1",dataType: dataType,catalogueId:"Model${index}",catalogueVersion:"v1").save(failOnError: true)
			def dataElement = new DataElement(name:"DE${index}",version: "1",description:"Desc${index}",catalogueId:"Model${index}",catalogueVersion:"v1",valueDomain:valueDomain )
			Model.list()[2].addToDataElements(dataElement).save(failOnError: true)
		}
	}


	@Unroll
	def "index is called to get all DataElements (total=#total , max=#max , offset=#offset , resultCount=#resultCount), it returns all DataElements in JSON"() {
		given:"A number of dataElements is available"
		DataElement.list().size() == 10


		when:"index is called with total,max,offset and resultCount params"
		params.offset = offset
		params.max = max
		controller.index()
		def model = controller.modelAndView.model

		then:"returns a list of dataElements with total,max,offset and objects"
		model.pagedResultListInstanceMap.total == total
		model.pagedResultListInstanceMap.max == max    //default records per page
		model.pagedResultListInstanceMap.offset == offset  //default offset
		model.pagedResultListInstanceMap.objects.size() == resultCount

		where:
		total   | max   | offset    | resultCount
		10      |  3    |   0       | 3
		10      |  3    |   6       | 3
		10      |  4    |   8       | 2
	}

	@Unroll
	def "index is called to get all DataElements of a Model by passing ModelId - nested RestPath"()	{
		when:"index is called with modelId params"
		def mcModel = Model.list()[modelIndex]
		params.ModelId = mcModel.id
		controller.index()
		def model = controller.modelAndView.model


		then:"returns a list of dataElements of that specific model"
		model.pagedResultListInstanceMap.total == mcModel.dataElements.size()
		model.pagedResultListInstanceMap.objects.size() == mcModel.dataElements.size()
		model.pagedResultListInstanceMap.objects[0].id == mcModel.dataElements[0].id
		model.pagedResultListInstanceMap.objects[0].valueDomain.id == mcModel.dataElements[0].valueDomain.id
		model.pagedResultListInstanceMap.objects[0].valueDomain.dataType.id == mcModel.dataElements[0].valueDomain.dataType.id

		where:
		modelIndex	<< [0,1,2]
	}


	@Unroll
	def "index called by a filter returns filtered DataElements"() {
		when:"index is called with modelId params"
		params["filters"] = "{name:'DEM1'}"
		controller.index()
		def model = controller.modelAndView.model

		then:"returns a list of dataElements of that specific model with that filter"
		model.pagedResultListInstanceMap.total == 5
		model.pagedResultListInstanceMap.objects[0].name.startsWith("DEM1")
	}


 	def "index returns no DataElements when called with ModelId which is not available"() {
		when:"index is called with no modelId params"
		params.ModelId = 9999400 //an unavailable-model
		controller.index()
		def model = controller.modelAndView.model

		then:"returns no dataElements"
		model.arrayListInstanceMap.objects.size() == 0
	}

	def "index sorts results based on name order 'asc' by default"(){

		when:"index is called"
		def des = DataElement.listOrderByName()
		controller.index()

		then:"result is sorted based on name order 'asc' by default "
		model.pagedResultListInstanceMap.objects[0].name == des.first().name
	}

}