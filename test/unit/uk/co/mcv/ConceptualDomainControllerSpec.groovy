package uk.co.mcv

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import uk.co.mcv.api.v1.ConceptualDomainController
import uk.co.mcv.model.ConceptualDomain

/**
 * Created by soheil on 06/05/2014.
 */
@TestFor(ConceptualDomainController)
@Mock(ConceptualDomain)
class ConceptualDomainControllerSpec extends  Specification {


	def setup(){
		controller.conceptualDomainService = Mock(ConceptualDomainService)
	}


	def "index is called, it returns conceptualDomains"(){
		when:""
		params.max = 30
		params.offset = 0
		controller.index()

		then:""
		2 * controller.conceptualDomainService.countAll(params) >> { return 3 }
		1 * controller.conceptualDomainService.listAll(params) >> { return [{},{},{}] }
		controller.modelAndView.model.conceptualDomainCount == 3
		controller.modelAndView.model.arrayListInstanceMap.objects.size() == 3
		controller.modelAndView.model.arrayListInstanceMap.total == 3
	}
}
