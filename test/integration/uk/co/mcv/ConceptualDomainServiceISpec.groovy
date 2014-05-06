package uk.co.mcv

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.spock.IntegrationSpec
import spock.lang.Specification
import uk.co.mcv.model.ConceptualDomain
import uk.co.mcv.model.DataElement
import uk.co.mcv.model.DataType
import uk.co.mcv.model.Model
import uk.co.mcv.model.ValueDomain

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
class ConceptualDomainServiceISpec extends IntegrationSpec {


	def conceptualDomainService

	def setup(){
		def conDomain = new ConceptualDomain(name:"NHIC", description: "NHIC conceptual domain",catalogueId: "1",catalogueVersion: "1").save(failOnError: true)

		//Create 5 sample Models
		(1..5).each { index ->
			def model = new Model(name:"Model${index}",catalogueId:"Model${index}",catalogueVersion:"v1")
			ConceptualDomain.list()[0].addToModels(model)
			model.save(failOnError: true)


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

	def	"delete will delete the conceptualDomain,its models and dataElements"(){

		when:""
		def cDomain = ConceptualDomain.list()[0]
		def models = Model.list()
		def cModels = cDomain.models.toList()

		conceptualDomainService.delete(cDomain)

		then:""
		DataElement.count() == 0
		Model.count() == 0
		ConceptualDomain.count() == 0

	}

}
