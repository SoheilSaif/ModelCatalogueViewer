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
class ModelServiceISpec extends IntegrationSpec {

	def modelService

	def setup(){

	}

	def "getTopLevelModels will return parent models"(){

		given:"parent and sub models exist"
		//a subModel and a number of parent models already exists
		def conDomain1 = new ConceptualDomain(name:"NHIC", description: "NHIC conceptual domain",catalogueId: "1",catalogueVersion: "1").save(flush:true, failOnError: true)
		def conDomain2 = new ConceptualDomain(name:"NHIC", description: "NHIC conceptual domain",catalogueId: "1",catalogueVersion: "1").save(flush:true, failOnError: true)


		//add two parent model to conceptualDomain2
		def pModel = new Model(name:"ParentModel",catalogueId:"Model1",catalogueVersion:"v1" )
		conDomain2.addToModels(pModel)
		pModel.save(flush:true, failOnError: true)
		pModel = new Model(name:"ParentModel",catalogueId:"Model1",catalogueVersion:"v1" )
		conDomain2.addToModels(pModel)
		pModel.save(flush:true, failOnError: true)


		//add a parent model and two subModel to conceptualDomain1
		def parentModel = new Model(name:"ParentModel1",catalogueId:"Model1",catalogueVersion:"v1" )
		conDomain1.addToModels(parentModel)
		parentModel.save(flush:true, failOnError: true)
		(1..2).each {
			Model subModel = new Model(name:"subModel",catalogueId: "11",catalogueVersion: "V1")
			conDomain1.addToModels(subModel)
			parentModel.addToSubModels(subModel)
			subModel.save(flush:true, failOnError: true)
		}


		when:"getTopLevelModels is called for a conceptualDomain"
		def topLevelModels = modelService.getTopLevelModels(conDomain1)

		then:"returns just top models"
		topLevelModels.size() == 1
		topLevelModels[0].name == "ParentModel1"
	}
}