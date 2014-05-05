package uk.co.mcv

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.spock.IntegrationSpec
import spock.lang.Specification
import uk.co.mcv.model.*

class ConceptualDomainServiceISpec extends IntegrationSpec {

	def conceptualDomainService

	def setup() {

		def conDomain = new ConceptualDomain(name:"NHIC", description: "NHIC conceptual domain",catalogueId: "1",catalogueVersion: "1").save(flush: true)
		def model = new Model(name:"Model",catalogueId:"Model",catalogueVersion:"v1")
		conDomain.addToModels(model)
		model.save(flush:true, failOnError: true)

		def subModel = new Model(name:"SubModel",catalogueId:"Model",catalogueVersion:"v1")
		model.addToSubModels(subModel)
		conDomain.addToModels(subModel)
		subModel.save(flush:true, failOnError: true)

		def dt = new DataType(enumerated: false, name: "d",catalogueId: "d",catalogueVersion: "d").save(failOnError: true)
		def vd = new ValueDomain(name: "d",catalogueId: "d",catalogueVersion: "d")
		conDomain.addToValueDomains(vd)
		dt.addToValueDomains(vd)
		vd.save(failOnError: true)

		def de = new DataElement(name:"DE",catalogueId: "d",catalogueVersion: "d")
		def vd0 = ValueDomain.list()[0]
		def m0 = Model.list()[0]
		vd0.addToDataElements(de)
		m0.addToDataElements(de)
		de.save(failOnError: true)

		def subDe = new DataElement(name:"sub-DE",catalogueId: "d",catalogueVersion: "d")
		vd0.addToDataElements(subDe)
		m0.addToDataElements(subDe)
		de.addToSubElements(subDe)
		subDe.save(failOnError: true)

	}


	def "deleteConceptualDomain will delete the conceptualDomain and its models"() {

		given:"A conceptualDomain and its models exist"
		def cdCountBefore = ConceptualDomain.count()
		def mlCountBefore = Model.count()
		def conDomain = ConceptualDomain.list()[0]
		def dtBefore = DataType.count()
		def vdBefore = ValueDomain.count()
		def deBefore = DataElement.count()
		def models = conDomain.models.collect()

		when:"deleteConceptualDomain is called for a conceptualDomain"
		conceptualDomainService.deleteConceptualDomain(conDomain)
		def cdCountAfter = ConceptualDomain.count()
		def mlCountAfter = Model.count()
		def dtAfter = DataType.count()
		def vdAfter = ValueDomain.count()
		def deAfter = DataElement.count()

		then:"the conceptualDomain and its models are all removed"
		mlCountAfter == mlCountBefore - 2
		!Model.list().containsAll(models)
		cdCountAfter == cdCountBefore - 1
		!ConceptualDomain.exists(conDomain.id)

	}
}
