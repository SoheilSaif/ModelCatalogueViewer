package uk.co.mcv

import grails.test.mixin.TestFor
import grails.test.spock.IntegrationSpec
import spock.lang.Specification
import uk.co.mcv.model.Author
import uk.co.mcv.model.AuthorBelong
import uk.co.mcv.model.Location
import uk.co.mcv.model.LocationBelong

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
class AuthorBelongSpec extends IntegrationSpec {

	def setup() {
	}

	def cleanup() {
	}

	void "Author Location, check for delete"() {

		when:""
		LocationBelong locationBelong = new LocationBelong(name:"L")
		def authorBelong = new AuthorBelong(name:"A",locationBelong:locationBelong)
		authorBelong.save(flush: true)

		def authorCountBefore = AuthorBelong.count()
		def locCountBefore = LocationBelong.count()

		then:""
		AuthorBelong.list()[0].delete(flush: true)
		AuthorBelong.count() == authorCountBefore - 1
		LocationBelong.count() == locCountBefore - 1
	}
}