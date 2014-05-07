package uk.co.mcv

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.spock.IntegrationSpec
import spock.lang.Specification
import spock.lang.Unroll
import uk.co.mcv.api.v1.DataElementController
import uk.co.mcv.model.ConceptualDomain
import uk.co.mcv.model.DataElement
import uk.co.mcv.model.DataType
import uk.co.mcv.model.Model
import uk.co.mcv.model.ValueDomain

/**
 * Created by soheil on 23/04/2014.
 */
@TestFor(DataElementController)
@Mock([Model,ConceptualDomain,DataElement,DataType,ValueDomain])
class DataElementControllerSpec extends  Specification{

	def setup(){
		controller.dataElementService = Mock(DataElementService)
	}


	def "index is called, it returns dataElements"(){
		when:""
		params.max = 30
		params.offset = 0
		controller.index()

		then:""
		2 * controller.dataElementService.countAll(params) >> { return 3 }
		1 * controller.dataElementService.listAll(params) >> { return [{},{},{}] }
		controller.modelAndView.model.dataElementCount == 3
		controller.modelAndView.model.arrayListInstanceMap.objects.size() == 3
		controller.modelAndView.model.arrayListInstanceMap.total == 3
	}
}