package uk.co.mcv

import grails.test.mixin.TestFor
import grails.test.spock.IntegrationSpec
import org.apache.http.auth.AUTH
import spock.lang.Specification
import uk.co.mcv.model.Author
import uk.co.mcv.model.Location

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
class AuthorSpec extends IntegrationSpec {

	def setup() {
	}

	def cleanup() {
	}

	void "Author Location, check for delete"() {

		when:""
		Location location = new Location(name:"L").save(flush: true)
		new Author(name:"A",location:location).save(flush: true)

		def authorCountBefore = Author.count()
		def locCountBefore = Location.count()

		then:""
		Author.list()[0].delete(flush: true)
		Author.count() == authorCountBefore - 1
		Location.count() == locCountBefore
	}
}