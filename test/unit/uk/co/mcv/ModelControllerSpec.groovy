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

//		Create 36 top level sample Models
//        (1..18).each { index ->
//            new Model(name:"Model${index}",catalogueId: "catId${index}",catalogueVersion: "version1",conceptualDomain: conDomain).save(failOnError: true)
//            new Model(name:"Model${index}",catalogueId: "catId${index}",catalogueVersion: "version2", conceptualDomain: conDomain).save(failOnError: true)
//        }
//
//		add a subModel
//		Model subModel1 = new Model(name:"subModelXX",catalogueId: "11",catalogueVersion: "V1",conceptualDomain: conDomain,parentModel: Model.list()[0]).save(failOnError: true)
//		Model.list()[0].addToSubModels(subModel1)
//		Model.list()[0].save(failOnError: true)

    }

//    @Unroll
//    def "index is called to get all Models for (total=#total , max=#max , offset=#offset , resultCount=#resultCount, filter=#filters), it returns all Model"()  {
//        given:"A number of Model is available"
//        Model.list().size() == 18
//
//
//        when:"index is called with total,max,offset and resultCount params"
//        params.offset = offset
//        params.max = max
//        params.filters = filters
//        controller.index()
//		def model = controller.modelAndView.model
//
//        then:"returns a list of models in json with total,max,offset and objects"
//		model.pagedResultListInstanceMap.total == total
//		model.pagedResultListInstanceMap.max == max    //default records per page
//		model.pagedResultListInstanceMap.offset == offset  //default offset
//		model.pagedResultListInstanceMap.objects.size() == resultCount
//
//        where:
//        total   | max   | offset    |	filters				| resultCount
//        37      |  10   |   0       |	"{}"				|	10
//        37      |  10   |   30      |	"{}"				|	7
//        37      |  20   |   31      | 	"{}"				|	6
//        20      |  10   |   0	    | 	"{'name':'Model1'}"	|	10
//		0       |  10   |   0	    | 	"{'name':'XYZ'}"	|	0
//        2       |  10   |   0	    | 	"{'name':'Model11'}"|	2
//	}
//



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



//	@Unroll
//	def "index with topLevels params returns all topLevel Models"()  {
//
//		given:"a number of top level model exists"
//		def topLevelModels = []
//		(1..5).each {
//			topLevelModels.add(new Model(name:"model"))
//		}
//
//		when:"index is called"
//		params.topLevels = true
//		controller.index()
//		def model = controller.modelAndView.model
//
//		then:"returns a list of top level models"
//		1 * controller.modelService.getTopLevelModels() >> { return topLevelModels }
//		model.modelCount == topLevelModels.size()
//		model.arrayListInstanceMap.total == topLevelModels.size()
//		model.arrayListInstanceMap.objects.size() == topLevelModels.size()
//	}
}
