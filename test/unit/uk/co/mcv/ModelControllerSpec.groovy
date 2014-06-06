package uk.co.mcv

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.codehaus.groovy.grails.web.json.JSONArray
import org.springframework.http.HttpStatus
import spock.lang.Specification
import spock.lang.Unroll
import uk.co.mcv.api.v1.ModelController
import uk.co.mcv.model.*

/**
 * Created by soheil on 08/04/2014.
 */
@TestFor(ModelController)
@Mock([Model,ConceptualDomain])
class ModelControllerSpec extends  Specification{


	def setup()  {
		controller.modelService = Mock(ModelService)
    }

    def "Check if ModelController is a readOnly Controller"() {
        when:"Save is called"
        controller.save()

        then:"it returns METHOD_NOT_ALLOWED HTTP status as controller is readOnly"
        response.status == HttpStatus.METHOD_NOT_ALLOWED.value()

        when:"Update is called"
        controller.update()

        then:"it returns METHOD_NOT_ALLOWED HTTP status as controller is readOnly"
        response.status == HttpStatus.METHOD_NOT_ALLOWED.value()


        when:"Delete is called"
        controller.delete()

        then:"it returns METHOD_NOT_ALLOWED HTTP status as controller is readOnly"
        response.status == HttpStatus.METHOD_NOT_ALLOWED.value()
    }



	def "index is called, it returns models"(){
		when:""
		params.max = 30
		params.offset = 0
		controller.index()

		then:""
		2 * controller.modelService.countAll(params) >> { return 3 }
		1 * controller.modelService.listAll(params) >> { return [{},{},{}] }
		controller.modelAndView.model.modelCount == 3
		controller.modelAndView.model.arrayListInstanceMap.objects.size() == 3
		controller.modelAndView.model.arrayListInstanceMap.total == 3
	}
}