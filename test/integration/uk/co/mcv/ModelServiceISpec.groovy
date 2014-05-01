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

	def "deleteModel will delete the model and its subModels"() {
		given:"a model already exists"
		def nhicConDomain = ConceptualDomain.list()[0]
		Model parentModel = Model.list()[0]
		Model subModel = new Model(name:"11",catalogueId: "11",catalogueVersion: "V1",conceptualDomain: nhicConDomain,parentModel: parentModel).save(failOnError: true)
		parentModel.addToSubModels(subModel)
		parentModel.save(failOnError: true)
		def modelBefore = Model.count()


		when:"deleteModel is called"
		modelService.deleteModel(parentModel)
		def modelAfter = Model.count()

		then:"the model and its subModels are all removed"
		!Model.exists(parentModel.id)
		!Model.exists(subModel.id)
		modelAfter ==  modelBefore - 2
	}



	def "addSubModel adds a subModel to a parent Model"(){

		given:"parent and sub models exist"
		def nhicConDomain = ConceptualDomain.list()[0]
		Model topModel = Model.list()[0]
		Model subModel1 = new Model(name:"11",catalogueId: "11",catalogueVersion: "V1",conceptualDomain: nhicConDomain).save(failOnError: true)

		when:"addSubModel is called"
		modelService.addSubModel(topModel,subModel1)

		then:"the sub model is added to the parent model"
		Model.list()[0].subModels
		Model.list()[0].subModels.size() == 1
		subModel1.parentModel.id == Model.list()[0].id
	}

	def "getTopLevelModels will return parent models"(){

		given:"parent and sub models exist"
		def nhicConDomain = ConceptualDomain.list()[0]
		Model parentModel = Model.list()[0]
		Model subModel = new Model(name:"11",catalogueId: "11",catalogueVersion: "V1",conceptualDomain: nhicConDomain,parentModel:parentModel).save(failOnError: true)
		parentModel.addToSubModels(subModel)
		parentModel.save(failOnError: true)
		new Model(name:"11",catalogueId: "11",catalogueVersion: "V1",conceptualDomain: nhicConDomain).save(failOnError: true)



		when:"getTopLevelModels is called"
		def topLevelModels = modelService.getTopLevelModels()

		then:"the sub model is added to the parent model"
		topLevelModels.size() == 6
		Model.list().size() == 7
	}
}