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

		def conDomain = new ConceptualDomain(name:"NHIC", description: "NHIC conceptual domain",catalogueId: "1",catalogueVersion: "1").save(failOnError: true)

		//Create 18 sample Models
        (1..18).each { index ->
            new Model(name:"Model${index}",catalogueId: "catId${index}",catalogueVersion: "version1",conceptualDomain: conDomain).save(failOnError: true)
            new Model(name:"Model${index}",catalogueId: "catId${index}",catalogueVersion: "version2", conceptualDomain: conDomain).save(failOnError: true)
        }
    }

    @Unroll
    def "index is called to get all Models for (total=#total , max=#max , offset=#offset , resultCount=#resultCount, filter=#filters), it returns all Model"()  {
        given:"A number of Model is available"
        Model.list().size() == 18


        when:"index is called with total,max,offset and resultCount params"
        params.offset = offset
        params.max = max
        params.filters = filters
        controller.index()
		def model = controller.modelAndView.model

        then:"returns a list of models in json with total,max,offset and objects"
		model.pagedResultListInstanceMap.total == total
		model.pagedResultListInstanceMap.max == max    //default records per page
		model.pagedResultListInstanceMap.offset == offset  //default offset
		model.pagedResultListInstanceMap.objects.size() == resultCount

        where:
        total   | max   | offset    |	filters				| resultCount
        36      |  10   |   0       |	"{}"				|	10
        36      |  10   |   30      |	"{}"				|	6
        36      |  20   |   31      | 	"{}"				|	5
        20      |  10   |   0	    | 	"{'name':'Model1'}"	|	10
        2       |  10   |   0	    | 	"{'name':'Model11'}"|	2

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

}
