package uk.co.mcv

import grails.test.spock.IntegrationSpec
import uk.co.mcv.model.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
class ModelServiceISpec extends IntegrationSpec {

	def modelService

    def setup() {

		def conDomain = new ConceptualDomain(name:"NHIC", description: "NHIC conceptual domain",catalogueId: "1",catalogueVersion: "1").save(failOnError: true)


		//Create 5 sample Models
		(1..5).each { index ->
			new Model(name:"Model${index}",catalogueId:"Model${index}",catalogueVersion:"v1",conceptualDomain: conDomain  ).save(flush: true)
		}

		//add some DataElements into Model[0]
		(1..5).each { index ->
			def dataType = new DataType(name:"DT${index}",version: "1",enumerated: false,catalogueId:"Model${index}",catalogueVersion:"v1").save(failOnError: true)
			def valueDomain = new ValueDomain(name:"VD${index}",version: "1",dataType: dataType,catalogueId:"Model${index}",catalogueVersion:"v1").save(failOnError: true)
			def dataElement = new DataElement(name:"DE${index}",version: "1",description:"Desc${index}",valueDomain:valueDomain,catalogueId:"Model${index}",catalogueVersion:"v1" )
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


	def "deleteModel will delete the model and all its dataElements"() {

		given:"a model already exists"
		def model = Model.list()[0]
		def modelDe = model.dataElements.size()
		def deBefore = DataElement.count()

		when:"deleteModel is called"
		modelService.deleteModel(model)
		def deAfter = DataElement.count()

		then:"the model and its dataElements are all removed"
		!Model.exists(model.id)
		deAfter ==  deBefore - modelDe
	}
}
