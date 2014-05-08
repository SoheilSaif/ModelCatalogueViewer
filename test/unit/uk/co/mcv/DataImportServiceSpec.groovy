package uk.co.mcv

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll
import uk.co.mcv.model.ConceptualDomain
import uk.co.mcv.model.DataElement
import uk.co.mcv.model.DataType
import uk.co.mcv.model.Model
import uk.co.mcv.model.ValueDomain

import java.lang.reflect.Array

/**
 * Created by soheil on 07/05/2014.
 */

@TestFor(DataImportService)
@Mock([Model, ConceptualDomain, DataType,ValueDomain,DataElement])
class DataImportServiceSpec extends Specification {


	def "getHeaders returns default valid headers"() {

		when: "getHeaders"
		def headersMap = service.getHeaders()

		then: " returns correct default headers"
		headersMap.dataElementCodeRow == "Data Item Unique Code"
		headersMap.dataElementNameRow == "Data Item Name"
		headersMap.dataElementDescriptionRow == "Data Item Description"
		headersMap.dataTypeRow == "Data type"
		headersMap.parentModelNameRow == "Parent Model"
		headersMap.parentModelCodeRow == "Parent Model Unique Code"
		headersMap.containingModelNameRow == "Model"
		headersMap.containingModelCodeRow == "Model Unique Code"
		headersMap.measurementUnitNameRow == "Measurement Unit"
		headersMap.metadataRow == "Metadata"
	}

	def "validateHeaders will check the headers name"() {

		when: "validateHeaders is called with given headers"
		def result = service.validateHeaders(headers)

		then: "returns result and missed columns"
		result[0] == resultValue
		result[1] == messageValue

		where: ""
		resultValue | messageValue                            | headers
		true        | ""                                      | getHeadersFixture()
		false       | "Data Item Unique Code"                 | getHeadersFixture(["Data Item Unique Code"])
		false       | "Data Item Unique Code, Data Item Name" | getHeadersFixture(["Data Item Unique Code", "Data Item Name"])
	}

	private ArrayList getHeadersFixture(def shouldBeRemoved) {
		def headers =
				["Data Item Unique Code",
						"Data Item Name",
						"Data Item Description",
						"Data type",
						"Parent Model",
						"Parent Model Unique Code",
						"Model",
						"Model Unique Code",
						"Measurement Unit",
						"Metadata"]


		shouldBeRemoved.each { value ->
			headers.remove(value)
		}
		return headers
	}


	@Unroll
	def "matchOrAddDataType will add a new DataType for #dataElementName"() {
		given: "A number of DataTypes already exist"
		(1..5).each { index ->
			new DataType(name: "dataType${index}", catalogueId: "${index}", catalogueVersion: "v", enumerated: false).save(failOnError: true, flush: true)
		}
		(6..10).each { index ->
			new DataType(name: "dataType${index}", catalogueId: "${index}", catalogueVersion: "v", enumerated: true, enumerations: ["1": "male", "2": "female"]).save(failOnError: true, flush: true)
		}

		when: "matchOrAddDataType is called"
		def result = service.matchOrAddDataType(dataElementName, dataTypeString)

		then: "it returns [status,dataType,message]"
		result[0] == status
		result[1]?.name == dataTypeName
		result[1]?.enumerated == enumerated
		result[1]?.enumerations == enumeration
		result[2] == message

		where: ""
		dataElementName | dataTypeString    | enumerated | status | enumeration                  | dataTypeName | message
		"de1"           | "dataType1"       | false      | true   | [:]                          | "dataType1"  | ""
		"de2"           | "dataTypeX1"      | false      | true   | [:]                          | "dataTypeX1" | ""
		"de3"           | "xs:string"       | false      | true   | [:]                          | "xs:string"  | ""
		"de4"           | "1:male|2:female" | true       | true   | ["1": "male", "2": "female"] | "de4"        | ""
		"de5"           | "1:male;2:female" | true       | true   | ["1": "male", "2": "female"] | "de5"        | ""
		"de6"           | ""                | false      | true   | [:]                          | "de6"        | ""
		"de7"           | "1:male;2female"  | null       | false  | null                         | null         | "Invalid format for DataType enum"
		"de8"           | "1:male|2female"  | null       | false  | null                         | null         | "Invalid format for DataType enum"
	}


	@Unroll
	def "addModel adds a new model for fixture:#fixtureId"() {
		given: "A number of models already exist"
		def conDomain = new ConceptualDomain(name: "A", description: "A", catalogueId: "1", catalogueVersion: "1").save(flush: true, failOnError: true)

		def model1 = new Model(name: "Model1", catalogueId: "Model1-CId", catalogueVersion: "v1")
		conDomain.addToModels(model1)
		model1.save(flush: true, failOnError: true)

		def model11 = new Model(name: "Model11", catalogueId: "Model11-CId", catalogueVersion: "v1")
		conDomain.addToModels(model11)
		model1.addToSubModels(model11)
		model11.save(flush: true, failOnError: true)


		def model2 = new Model(name: "Model2", catalogueId: "Model2-CId", catalogueVersion: "v1")
		conDomain.addToModels(model2)
		model2.save(flush: true, failOnError: true)


		when: "addModel is called"
		def modelResult = service.addModel(parentCode, parentName, modelCode, modelName, conDomain)

		then: ""
		modelResult[0] == status
		modelResult[1]?.catalogueId == modelCatalogueId
		modelResult[1]?.parentModel?.catalogueId == parentCatalogueId
		modelResult[2] == message


		where: ""
		fixtureId | parentCode      | parentName  | modelCode      | modelName  | status | modelCatalogueId | parentCatalogueId | message
		1         | ""              | ""          | ""             | ""         | false  | null             | null              | "Parent Model Code or Model Code should be defined."
		2         | "Model1-CId"    | "Model1"    | ""             | ""         | true   | "Model1-CId"     | null              | ""
		3         | "ModelNEW-CId"  | "ModelNEW"  | ""             | ""         | true   | "ModelNEW-CId"   | null              | ""

		4         | ""              | ""          | "Model1-CId"   | "Model1"   | true   | "Model1-CId"     | null              | ""
		5         | ""              | ""          | "ModelNEW-CId" | "ModelNEW" | true   | "ModelNEW-CId"   | null              | ""

		6         | "Model1-CId"    | "Model1"    | "Model11-CId"  | "Model11"  | true   | "Model11-CId"    | "Model1-CId"      | ""
		7         | "Model2-CId"    | "Model2"    | "Model11-CId"  | "Model11"  | false  | null             | null              | "Model has a different parent! can not change model parent."

		8         | "Model1-CId"    | "Model1"    | "ModelNEW-CId" | "ModelNEW" | true   | "ModelNEW-CId"   | "Model1-CId"      | ""

		9         | "ModelPNEW-CId" | "ModelPNEW" | "ModelNEW-CId" | "ModelNEW" | true   | "ModelNEW-CId"   | "ModelPNEW-CId"   | ""
		10        | "ModelPNEW-CId" | "ModelPNEW" | "Model11-CId"  | "Model11"  | false  | null             | null              | "Model has a different parent! can not change model parent."

	}


	def "addDataElement adds a new dataElement"() {

		given:"A number of dataElements exist"
		def conDomain = new ConceptualDomain(name:"NHIC", description: "NHIC conceptual domain", catalogueId: "1", catalogueVersion: "1").save(flush:true,failOnError: true)

		def model = new Model(name:"Model1", catalogueId:"Model1", catalogueVersion:"v1")
		conDomain.addToModels(model)
		model.save(flush:true, failOnError: true)

		def dataType = new DataType(enumerated: false, name: "d",catalogueId: "d", catalogueVersion: "d").save(flush:true,failOnError: true)
		def valueDomain = new ValueDomain(name: "d", catalogueId: "d", catalogueVersion: "d",dataType: dataType)
		conDomain.addToValueDomains(valueDomain)
		valueDomain.save(flush:true, failOnError: true)

		def dataElement = new DataElement(name:"De-Name", description:"this is dataelement" ,catalogueId: "DE1", catalogueVersion: "d")
		valueDomain.addToDataElements(dataElement)
		model.addToDataElements(dataElement)
		dataElement.save(flush:true,failOnError: true)

		def deCountBefore = DataElement.count()
		def vdCountBefore = ValueDomain.count()

		when:"addDataElement is called"
		def deResult = service.addDataElement(name, description, catalogueId, [:], model, dataType,"measurementUnitName",conDomain)

		then:"a new dataElement is added"
		deResult[0] == result
		deResult[1]?.name == name
		deResult[1]?.description == description
		deResult[2] == message
		DataElement.count() == 1  + deCountBefore
		ValueDomain.count() == 1  + vdCountBefore

		where:""
		catalogueId	|	name  |	description	|result	| message
		"DE2"		|	"DE2" |	"DE2-Desc"	| true	| ""
	}
}