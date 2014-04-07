package modelcatalogueviewer



import grails.test.mixin.*
import spock.lang.*

@TestFor(DataElementLocalController)
@Mock(DataElementLocal)
class DataElementLocalControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.dataElementLocalInstance
            model.dataElementLocalInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.dataElementLocalInstance!= null
    }

}
