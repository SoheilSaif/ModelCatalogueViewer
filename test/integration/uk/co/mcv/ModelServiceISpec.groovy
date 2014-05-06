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
		def conDomain = new ConceptualDomain(name:"NHIC", description: "NHIC conceptual domain",catalogueId: "1",catalogueVersion: "1").save(flush:true, failOnError: true)

		def parentModel = new Model(name:"ParentModel",catalogueId:"Model1",catalogueVersion:"v1" )
		conDomain.addToModels(parentModel)
		parentModel.save(flush:true, failOnError: true)


		Model subModel = new Model(name:"subModel1",catalogueId: "11",catalogueVersion: "V1")
		conDomain.addToModels(subModel)
		parentModel.addToSubModels(subModel)
		subModel.save(flush:true, failOnError: true)


		when:"getTopLevelModels is called"
		def topLevelModels = modelService.getTopLevelModels()

		then:"returns just top models"
		topLevelModels.size() == 1
		!topLevelModels.contains(subModel)
	}
}