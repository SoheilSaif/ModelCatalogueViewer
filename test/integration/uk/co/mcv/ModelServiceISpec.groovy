package uk.co.mcv

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.spock.IntegrationSpec
import org.dom4j.rule.Mode
import spock.lang.Ignore
import spock.lang.Specification
import uk.co.mcv.model.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@Mock([Model,ConceptualDomain,DataElement,DataType,ValueDomain])
class ModelServiceISpec extends IntegrationSpec {

	def modelService

    def setup() {

		def conDomain = new ConceptualDomain(name:"NHIC", description: "NHIC conceptual domain",catalogueId: "1",catalogueVersion: "1").save(failOnError: true)


		//Create 5 sample Models
		(1..5).each { index ->
			def model = new Model(name:"Model${index}",catalogueId:"Model${index}",catalogueVersion:"v1" )
			conDomain.addToModels(model)
			model.save(flush: true)

		}

		//add some DataElements into Model[0]
		(1..5).each { index ->
			def dataType = new DataType(name:"DT${index}",version: "1",enumerated: false,catalogueId:"Model${index}",catalogueVersion:"v1").save(failOnError: true)
			def valueDomain = new ValueDomain(name:"VD${index}",version: "1",catalogueId:"Model${index}",catalogueVersion:"v1")
			conDomain.addToValueDomains(valueDomain)
			dataType.addToValueDomains(valueDomain)
			valueDomain.save(failOnError: true)

			def dataElement = new DataElement(name:"DEM1-${index}",version: "1",description:"Desc${index}",catalogueId:"Model${index}",catalogueVersion:"v1" )
			valueDomain.addToDataElements(dataElement)
			Model.list()[0].addToDataElements(dataElement)
			dataElement.save(failOnError: true)
		}

	}


	def "deleteModel will delete the model and all its dataElements"() {
		given:"a model already exists"
		def model = Model.list()[0]
		def modelDeCount = model.dataElements.size()
		def allDeCountBefore = DataElement.count()


		when:"deleteModel is called"
		modelService.deleteModel(model)
		def allDeCountAfter = DataElement.count()

		then:"the model and its dataElements are all removed"
		!Model.exists(model.id)
		allDeCountAfter ==  allDeCountBefore - modelDeCount
	}

	@Ignore
	def "deleteModel will delete the model and its subModels"() {

		given:"a top model and two subModel already exist"
		def conDomain = ConceptualDomain.list()[0]
		Model parentModel = Model.list()[0]
		Model subModel1 = new Model(name:"11",catalogueId: "11",catalogueVersion: "V1",conceptualDomain: conDomain,parentModel: parentModel).save(failOnError: true)
		parentModel.addToSubModels(subModel1)
		parentModel.save(failOnError: true)

		Model subModel2 = new Model(name:"11",catalogueId: "11",catalogueVersion: "V1",conceptualDomain: conDomain,parentModel: parentModel).save(failOnError: true)
		parentModel.addToSubModels(subModel2)
		parentModel.save(failOnError: true)

		def modelBefore = Model.count()


		when:"deleteModel is called for parent model"
		modelService.deleteModel(parentModel)
		def modelAfter = Model.count()

		then:"the parent model and its subModels are all removed"
		!Model.exists(parentModel.id)
		!Model.exists(subModel1.id)
		!Model.exists(subModel2.id)
		modelAfter ==  modelBefore - 3
	}

	@Ignore
	def "deleteModel will delete the model and its subModels hierarchy"() {

		given:"a top model and two subModel already exist"
		def conDomain = ConceptualDomain.list()[0]
		Model parentModel = Model.list()[0]
		//add a subModel
		Model subModel1 = new Model(name:"11",catalogueId: "11",catalogueVersion: "V1",conceptualDomain: conDomain,parentModel: parentModel).save(failOnError: true)
		parentModel.addToSubModels(subModel1)
		parentModel.save(failOnError: true)

		//add a subModel to the subModel
		Model subModel11 = new Model(name:"11",catalogueId: "11",catalogueVersion: "V1",conceptualDomain: conDomain,parentModel: subModel1).save(failOnError: true)
		subModel1.addToSubModels(subModel11)
		subModel1.save(failOnError: true)

		def modelBefore = Model.count()


		when:"deleteModel is called for parent model"
		modelService.deleteModel(parentModel)
		def modelAfter = Model.count()

		then:"the parent model and its subModel hierarchy are all removed"
		!Model.exists(parentModel.id)
		!Model.exists(subModel1.id)
		!Model.exists(subModel11.id)
		modelAfter ==  modelBefore - 3
	}

	@Ignore
	def "addSubModel adds a subModel to a parent Model"(){

		setup:""
		def conDomain = ConceptualDomain.list()[0]
		Model topModel = Model.list()[0]
		Model subModel = new Model(name:"11",catalogueId: "11",catalogueVersion: "V1",conceptualDomain: conDomain).save(failOnError: true)

		when:"addSubModel is called"
		modelService.addSubModel(topModel,subModel)

		then:"the sub model is added to the parent model"
		Model.list()[0].subModels.size() == 1
		subModel.parentModel.id == Model.list()[0].id
	}


	@Ignore
	def "getTopLevelModels will return parent models"(){

		given:"parent and sub models exist"
		//a subModel and a number of parent models already exists
		def conDomain = ConceptualDomain.list()[0]
		Model parentModel = Model.list()[0]
		Model subModel = new Model(name:"11",catalogueId: "11",catalogueVersion: "V1",conceptualDomain: conDomain,parentModel:parentModel).save(failOnError: true)
		parentModel.addToSubModels(subModel)
		parentModel.save(failOnError: true)


		when:"getTopLevelModels is called"
		def topLevelModels = modelService.getTopLevelModels()

		then:"returns just top models"
		topLevelModels.size() == 5
		!topLevelModels.contains(subModel)
	}
}