import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import uk.co.mcv.api.v1.ModelController
import uk.co.mcv.model.Model

/**
 * Created by soheil on 08/04/2014.
 */
@TestFor(ModelController)
@Mock(Model)
class ModelControllerSpec extends  Specification{

    def setup()
    {
        new Model([name:"Model1"]).save(flush: true)
        new Model([name:"Model2"]).save(flush: true)
        new Model([name:"Model3"]).save(flush: true)
    }

//    def ""()
//    {
//        when:""
//        Model.count() == 0
//        controller.index()
//
//        then:""
//        controller.modelAndView.model.modelCount == 2
//    }

}
