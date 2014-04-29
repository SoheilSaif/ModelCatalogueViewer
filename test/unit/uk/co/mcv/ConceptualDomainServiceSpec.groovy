package uk.co.mcv

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import uk.co.mcv.model.ConceptualDomain
import uk.co.mcv.model.Model

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ConceptualDomainService)
@Mock([ConceptualDomain,Model])
class ConceptualDomainServiceSpec extends Specification {

	def "Having NHIC conceptualDomain, getNHIC wil return it"() {

		given:"having NHIC ConceptualDomain"
		new ConceptualDomain(name: "NHIC",description: "NHIC Model Catalogue",catalogueId: "1",catalogueVersion: "1").save(failOnError: true)
		ConceptualDomain.count() == 1
		def nhic = ConceptualDomain.first()

		when:"getNHIC is called"
		ConceptualDomain cd = service.getNHIC()

		then:"it returns the NHIC ConceptualDomain"
		cd.id == nhic.id
	}

	def "Not Having NHIC conceptualDomain, getNHIC will creates it and returns it"() {

		given:"No ConceptualDomain exists"
		ConceptualDomain.count() == 0

		when:"getNHIC is called"
		ConceptualDomain cd = service.getNHIC()

		then:"it creates and return the NHIC ConceptualDomain"
		ConceptualDomain.count() == 1
		cd.name == "NHIC"
	}


	def "getModels return all model of NHIC ConceptualDomain"() {

		given:"a number of models exists for NHIC"
		def conDomain = new ConceptualDomain(name: "NHIC",description: "NHIC Model Catalogue",catalogueId: "1",catalogueVersion: "1").save(failOnError: true)
		(1..5).each { index ->
			new Model(name:"Model${index}",catalogueId:"Model${index}",catalogueVersion:"v1",conceptualDomain: conDomain  ).save(flush: true)
		}
		when:"getModels is called"
		def models = service.getModels(conDomain)

		then:"all models are returned"
		models.size() == 5
	}
}
