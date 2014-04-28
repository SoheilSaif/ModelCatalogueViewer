import org.springframework.web.context.support.WebApplicationContextUtils
import uk.co.mcv.model.ConceptualDomain
import uk.co.mcv.model.DataElement
import uk.co.mcv.model.DataType
import uk.co.mcv.model.Model
import uk.co.mcv.model.ValueDomain

class BootStrap {

    def init = { servletContext ->

        def springContext = WebApplicationContextUtils.getWebApplicationContext(servletContext)
        springContext.getBean( "customObjectMarshallers" ).register()

        environments {
            production {
                //createBaseRoles()
                //createAdminAccount()
            }
            staging{
                BuildTestData()
            }
            test{
                BuildTestData()
            }
            development {
                BuildTestData()
            }
        }

    }
    def destroy = {
    }



    def  BuildTestData()
    {
		def nhicConDomain = new ConceptualDomain(name:"NHIC", description: "NHIC conceptual domain").save(failOnError: true)
        Model model1 = new Model(name:"Ovarian Cancer").save(failOnError: true)
        Model model2 = new Model(name:"Acute Coronary Syndromes").save(failOnError: true)
		Model model3 = new Model(name:"Renal Transplantation").save(failOnError: true)
		Model model4 = new Model(name:"Viral Hepatitis C/B").save(failOnError: true)
		Model model5 = new Model(name:"Intensive Care").save(failOnError: true)

		nhicConDomain.addToModels(model1)
		nhicConDomain.addToModels(model2)
		nhicConDomain.addToModels(model3)
		nhicConDomain.addToModels(model4)
		nhicConDomain.addToModels(model5)
		nhicConDomain.save(failOnError: true)


		def dataType = new DataType(name:"TestDataType", enumerated: false).save(failOnError: true)
		def valueDomain = new ValueDomain(name:"TestValueDomain", dataType: dataType).save(failOnError: true)

        def dataElement1 = new DataElement([name:"A", description:"A", definition:"A", valueDomain:valueDomain])

        def dataElement2 = new DataElement([name:"B", description:"B", definition:"B", valueDomain:valueDomain])

		model1.addToDataElements(dataElement1)
		model1.addToDataElements(dataElement2)

        model1.save(failOnError: true)
  }

}
