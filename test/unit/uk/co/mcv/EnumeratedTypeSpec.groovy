package uk.co.mcv

import grails.test.mixin.Mock
import spock.lang.Specification
import uk.co.mcv.model.EnumeratedType

/**
 * Created by soheil on 10/05/2014.
 */
@Mock(EnumeratedType)
class EnumeratedTypeSpec extends Specification {

	def "EnumeratedType saves properly"(){
		when:
		EnumeratedType enumeratedType = new EnumeratedType(name:"AAA",catalogueId: "SSS")
		def enums = [:]
		enums.put("1","male")
		enums.put("0","female")
		enumeratedType.enumerations =  enums
		enumeratedType.save(failOnError: true)

		then:
		EnumeratedType.count() == 1
		EnumeratedType.list()[0].enumAsString == "0:female|1:male"
		EnumeratedType.list()[0].enumerations["0"] == "female"
		EnumeratedType.list()[0].enumerations["1"] == "male"
	}
}