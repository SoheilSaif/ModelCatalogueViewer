import org.springframework.web.context.support.WebApplicationContextUtils
import uk.co.mcv.model.ConceptualDomain
import uk.co.mcv.model.DataElement
import uk.co.mcv.model.DataType
import uk.co.mcv.model.Model
import uk.co.mcv.model.ValueDomain
import uk.co.mcv.pathway.*

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
				BuildSamplePathway()
            }
            test{
                BuildTestData()
				//BuildSamplePathway()
			}
            development {
                BuildTestData()
				BuildSamplePathway()
			}
        }

    }
    def destroy = {
    }


	def BuildSamplePathway(){
		if(!Pathway.count()){

			//Add a form to the pathways
			def pathway1 = new Pathway(
					name: 'Transplanting and Monitoring Pathway',
					userVersion: '0.2',
					isDraft: true
			).save(failOnError: true)


			Node subPathway1 = new Node(
					name: 'Guarding Patient on recovery and transfer to nursing ward',
					description: 'transfer patient to the Operating Room',
					userVersion: '0.1',
					isDraft: true,
					x: '325px',
					y: '330px',
					parent: pathway1,
			).save(failOnError:true)

			Node node1 = new Node(
					name: 'Guard Patient',
					x: '250px',
					y: '0px',
					description: 'guard patient on recovery',
			).save(failOnError: true)

			Node node2 = new Node(
					name: 'Recovery',
					x: '150px',
					y: '100px',
					description: 'recover',
			).save(failOnError: true)

			Node node3 = new Node(
					name: 'Transfer to nursing ward',
					x: '250px',
					y: '300px',
					description: 'transfer patient to the nursing ward',
			).save(failOnError: true)

			def link1 = new Link(
					name: 'TM1',
					pathway: subPathway1,
					source: node1,
					target: node2,
			).save(failOnError:true)

			def link2 = new Link(
					name: 'TM2',
					pathway: subPathway1,
					source: node2,
					target: node3,
			).save(failOnError:true)

			subPathway1.addToNodes(node1)
			subPathway1.addToNodes(node2)
			subPathway1.addToNodes(node3)
			subPathway1.addToLinks(link1)
			subPathway1.addToLinks(link2)

			def node21 = new Node(
					name: 'transfer to O.R.',
					x: '455px',
					y: '0px',
					description: 'transfer patient to the Operating Room',
			).save(flush:true)


			def node22 = new Node(
					name: 'Anaesthesia and Operating Procedure',
					x: '115px',
					y: '110px',
					description: 'perform the operation',
			).save(flush:true)


			def dataElements = DataElement.list().collect()
			(1..15)*.each { index ->
				node22.addToDataElements(dataElements[index])
			}

			node22.save(flush: true)


			def link21 = new Link(
					name: 'TM21',
					source: node21,
					target: node22,
					pathway: pathway1,
			).save(flush:true)

			def link22 = new Link(
					name: 'TM22',
					source: node22,
					target: subPathway1,
					pathway: pathway1,
			).save(flush:true)


			pathway1.addToNodes(node21)
					.addToNodes(node22)
					.addToNodes(subPathway1)
					.addToLinks(link21)
					.addToLinks(link22)
		}
	}

    def  BuildTestData()
    {
		def nhicConDomain = new ConceptualDomain(name:"NHIC", description: "NHIC conceptual domain",catalogueId: "1",catalogueVersion: "1").save(failOnError: true)

        Model model1 = new Model(name:"Ovarian Cancer",catalogueId:"1",catalogueVersion: "1",conceptualDomain: nhicConDomain).save(failOnError: true)
        Model model2 = new Model(name:"Acute Coronary Syndromes",catalogueId:"2",catalogueVersion: "1",conceptualDomain: nhicConDomain  ).save(failOnError: true)
		Model model3 = new Model(name:"Renal Transplantation",catalogueId:  "3",catalogueVersion: "1" ,conceptualDomain: nhicConDomain).save(failOnError: true)
		Model model4 = new Model(name:"Viral Hepatitis C/B", catalogueId:  "4",catalogueVersion: "1" ,conceptualDomain: nhicConDomain).save(failOnError: true)
		Model model5 = new Model(name:"Intensive Care",catalogueId: "5",catalogueVersion: "1" ,conceptualDomain: nhicConDomain).save(failOnError: true)

		def dataType = new DataType(name:"TestDataType", enumerated: false,catalogueId: "1",catalogueVersion: "1").save(failOnError: true)
		def valueDomain = new ValueDomain(name:"TestValueDomain", dataType: dataType,catalogueId: "1",catalogueVersion: "1").save(failOnError: true)

		(1..30).each {index ->
			def dataElement = new DataElement(name:"M1-Name${index}", description:"Description${index}", definition:"A${index}", valueDomain:valueDomain,catalogueId: "1",catalogueVersion: "1")
			model1.addToDataElements(dataElement)
		}
        model1.save(failOnError: true)



		(1..30).each {index ->
			def dataElement = new DataElement(name:"M2-Name${index}", description:"Description${index}", definition:"A${index}", valueDomain:valueDomain,catalogueId: "1",catalogueVersion: "1")
			model2.addToDataElements(dataElement)
		}
		model2.save(failOnError: true)







  }

}
