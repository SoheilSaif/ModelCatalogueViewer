package uk.co.mcv

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.spock.IntegrationSpec
import spock.lang.Specification
import uk.co.mcv.model.*

class ConceptualDomainServiceISpec extends IntegrationSpec {

	def conceptualDomainService

	def setup() {

		def conDomain = new ConceptualDomain(name:"NHIC", description: "NHIC conceptual domain", catalogueId: "1", catalogueVersion: "1").save(flush:true,failOnError: true)
		def model = new Model(name:"Model", catalogueId:"Model", catalogueVersion:"v1")
		conDomain.addToModels(model)
		model.save(flush:true, failOnError: true)

		def subModel = new Model(name:"SubModel", catalogueId:"Model", catalogueVersion:"v1")
		model.addToSubModels(subModel)//do i really need it as its top model is attached to the conceptualDomain!
		conDomain.addToModels(subModel)
		subModel.save(flush:true, failOnError: true)

		def dataType = new DataType(enumerated: false, name: "d",catalogueId: "d", catalogueVersion: "d").save(flush:true,failOnError: true)

		//as ValueDomain has a DataType, but DataType does not have hasMany relation to valueDomain
		//so to relating a valueDomain with a DataTye, we just add it in ValueDomain constructor
		//and do not do like dt.addToValueDomains(dvd)
		def valueDomain = new ValueDomain(name: "d", catalogueId: "d", catalogueVersion: "d",dataType: dataType)
		conDomain.addToValueDomains(valueDomain)
		valueDomain.save(flush:true, failOnError: true)


		def dataElement = new DataElement(name:"DE", catalogueId: "d", catalogueVersion: "d")
		def valueDomain1 = ValueDomain.list()[0]
		def model1 = Model.list()[0]
		valueDomain1.addToDataElements(dataElement)
		model1.addToDataElements(dataElement)
		dataElement.save(flush:true,failOnError: true)

		def subDataElement = new DataElement(name:"sub-DE", catalogueId: "d", catalogueVersion: "d")
		valueDomain1.addToDataElements(subDataElement)
		model1.addToDataElements(subDataElement)
		dataElement.addToSubElements(subDataElement)
		subDataElement.save(flush:true,failOnError: true)
	}


	def "deleteConceptualDomain will delete the conceptualDomain and its models"() {

		given:"A conceptualDomain and its models, subModels, dataElements and valueDomains exist"
		def cdCountBefore = ConceptualDomain.count()
		def mlCountBefore = Model.count()
		def conDomain = ConceptualDomain.list()[0]
		def dtBefore = DataType.count()
		def vdBefore = ValueDomain.count()
		def deBefore = DataElement.count()
		def modelsBefore = conDomain.models.collect()

		when:"delete is called for a conceptualDomain"
		conceptualDomainService.delete(ConceptualDomain.list()[0])

		then:"the conceptualDomain and its models, subModels, dataElements and valueDomains are all removed"
		//the conceptualDomain should be deleted
		ConceptualDomain.count() == cdCountBefore - 1
		!ConceptualDomain.exists(conDomain.id)

		//all its models and related subModels should be deleted
		Model.count() == mlCountBefore - 2
		!Model.list().containsAll(modelsBefore)

		//all dataElements related to models should be deleted
		DataElement.count() == deBefore - 2

		//all its valueDomains should be deleted
		ValueDomain.count() == vdBefore - 1

		//no dataType should be deleted
		dtBefore == DataType.count()
	}
}
