package uk.co.mcv

import grails.test.mixin.TestFor
import grails.test.spock.IntegrationSpec
import spock.lang.Specification
import uk.co.mcv.model.A
import uk.co.mcv.model.B
import uk.co.mcv.model.C
import uk.co.mcv.model.D

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
class ASpec extends IntegrationSpec {

    def setup() {
    }

    def cleanup() {
    }

    void "A.delete will delete A,B,C hierarchy"() {

		when:"A has a number of b as children and b1 has 3 c children"
		def a = new A(name:"A").save(flush: true)
		def b1 = new B(name: "b1")
		def b2 = new B(name: "b2")
		a.addToBs(b1)
		a.addToBs(b2)
		a.save(flush: true)

		def c1 = new C(name: "C1")
		def c2 = new C(name: "C2")
		def c3 = new C(name: "C3")
		b1.addToCs(c1)
		b1.addToCs(c2)
		b1.addToCs(c3)
		b1.save(flush: true)
		def aCountBefore = A.count()
		def bCountBefore = B.count()
		def cCountBefore = C.count()

		then:""
		A.list()[0].delete(flush:true )
		A.count() == aCountBefore - 1
		B.count() == bCountBefore - 2
		C.count() == cCountBefore - 3
    }


	def ""(){

		when:""
		def dParent = new D(name:"DParent1").save(flush: true)
		dParent.addToDs(new D(name:"DChild1"))
		dParent.addToDs(new D(name:"DChild2"))
		dParent.save(flush: true)
		def dCountBefore = D.count()
		D.findByName("DParent1").delete(flush: true)

		then:""
		D.count() == dCountBefore - 3
	}
}
