package uk.co.mcv

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll
import uk.co.mcv.importer.ImportRow
import uk.co.mcv.model.ConceptualDomain
import uk.co.mcv.model.DataElement
import uk.co.mcv.model.DataElementValueDomain
import uk.co.mcv.model.DataType
import uk.co.mcv.model.EnumeratedType
import uk.co.mcv.model.MeasurementUnit
import uk.co.mcv.model.Model
import uk.co.mcv.model.ValueDomain


/**
 * Created by soheil on 07/05/2014.
 */

@TestFor(DataImportService)

@Mock([Model, ConceptualDomain, DataType, ValueDomain, DataElement, DataElementValueDomain, MeasurementUnit, EnumeratedType])
class DataImportServiceSpec extends Specification {

	static transactional = false


	def setup() {
		service.conceptualDomainService = Mock(ConceptualDomainService)
		service.dataElementValueDomainService = Mock(DataElementValueDomainService)
	}


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
			new DataType(name: "dataType${index}", catalogueId: "${index}", catalogueVersion: "v").save(failOnError: true, flush: true)
		}
		when: "matchOrAddDataType is called"
		def result = service.matchOrAddDataType(dataElementName, dataTypeString)

		then: "it returns [status,dataType,message]"
		result[0] == status
		result[1]?.name == dataTypeName
		result[2] == message

		where: ""
		dataElementName | dataTypeString   | status | dataTypeName | message
		"de1"           | "dataType1"      | true   | "dataType1"  | ""
		"de2"           | "dataTypeX1"     | true   | "dataTypeX1" | ""
		"de3"           | "xs:string"      | true   | "xs:string"  | ""
		"de7"           | "1:male;2female" | false  | null         | "Invalid format for DataType enum."
		"de8"           | "1:male|2female" | false  | null         | "Invalid format for DataType enum."
		"de9"           | ""               | false  | null         | "Blank DataType is not accepted."

	}


	@Unroll
	def "matchOrAddDataType will add a new EnumeratedType for #dataElementName"() {
		given: "A number of EnumeratedTypes already exist"
		def enumDt = new EnumeratedType(name: "enumDT", catalogueId: "1", catalogueVersion: "v")
		def enums = ["1": "male", "2": "female"]
		enumDt.enumerations = enums
		enumDt.save(failOnError: true, flush: true)

		when: "matchOrAddDataType is called"
		def result = service.matchOrAddDataType(dataElementName, dataTypeString)

		then: "it returns [status,dataType,message]"
		result[0] == status
		result[1]?.name == dataTypeName
		result[1]?.enumerations == enumerations
		result[2] == message

		where: ""
		dataElementName | dataTypeString            | status | enumerations                               | dataTypeName | message
		"de4"           | "1:male|2:female"         | true   | ["1": "male", "2": "female"]               | "enumDT"     | ""
		"de5"           | "1:male;2:female"         | true   | ["1": "male", "2": "female"]               | "enumDT"     | ""
		"de7"           | "1:male|2:female|3:other" | true   | ["1": "male", "2": "female", "3": "other"] | "de7"        | ""
		"de0"           | "1:male;2female"          | false  | null                                       | null         | "Invalid format for DataType enum."
		"de00"          | "1:male|2female"          | false  | null                                       | null         | "Invalid format for DataType enum."
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
		7         | "Model2-CId"    | "Model2"    | "Model11-CId"  | "Model11"  | false  | null             | null              | "Model has a different parent! you can not change parent model."

		8         | "Model1-CId"    | "Model1"    | "ModelNEW-CId" | "ModelNEW" | true   | "ModelNEW-CId"   | "Model1-CId"      | ""

		9         | "ModelPNEW-CId" | "ModelPNEW" | "ModelNEW-CId" | "ModelNEW" | true   | "ModelNEW-CId"   | "ModelPNEW-CId"   | ""
		10        | "ModelPNEW-CId" | "ModelPNEW" | "Model11-CId"  | "Model11"  | false  | null             | null              | "Model has a different parent! you can not change parent model."

	}


	def "addDataElement adds a new dataElement successfully"() {

		given: "A number of dataElements exist"
		def conDomain = new ConceptualDomain(name: "NHIC", description: "NHIC conceptual domain", catalogueId: "1", catalogueVersion: "1").save(flush: true, failOnError: true)

		def model = new Model(name: "Model1", catalogueId: "Model1", catalogueVersion: "v1")
		conDomain.addToModels(model)
		model.save(flush: true, failOnError: true)

		def dataElement = new DataElement(name: "De-Name", description: "this is dataelement", catalogueId: "DE1", catalogueVersion: "d")
		model.addToDataElements(dataElement)
		dataElement.save(flush: true, failOnError: true)

		def deCountBefore = DataElement.count()

		when: "addDataElement is called"
		def deResult = service.addDataElement(name, description, catalogueId, metadata, model)

		then: "a new dataElement is added"
		deResult[0] == true
		deResult[1]?.name == name
		deResult[1]?.description == description
		deResult[1]?.extensions  == metadata
		deResult[1]?.model.id == model.id //it is associated to the model properly
		deResult[2] == message
		DataElement.count() == 1 + deCountBefore

		where: ""
		catalogueId | name  | description | metadata                       | message
		"DE2"       | "DE2" | "DE2-Desc"  | ["code": "1123"]               | ""
		"DE2"       | "DE2" | null        | ["code": "123", "add": "1234"] | ""
		"DE2"       | "DE2" | null        | [:]                            | ""
	}


	def "addDataElement rejects dataElement with invalid values"() {

		given: "A number of dataElements exist"
		def conDomain = new ConceptualDomain(name: "NHIC", description: "NHIC conceptual domain", catalogueId: "1", catalogueVersion: "1").save(flush: true, failOnError: true)

		def model = new Model(name: "Model1", catalogueId: "Model1", catalogueVersion: "v1")
		conDomain.addToModels(model)
		model.save(flush: true, failOnError: true)

		def dataElement = new DataElement(name: "De-Name", description: "this is dataelement", catalogueId: "DE1", catalogueVersion: "d")
		model.addToDataElements(dataElement)
		dataElement.save(flush: true, failOnError: true)

		def deCountBefore = DataElement.count()

		when: "addDataElement is called"
		def deResult = service.addDataElement(name, description, catalogueId, [:], model)

		then: "addDataElement rejects invalid values"
		deResult[0] == false
		DataElement.count() == deCountBefore

		where: ""
		catalogueId | name  | description | result | message
		"DE2"       | ""    | "DE2-Desc"  | false  | "Blank DataElementName is not accepted."
		"DE2"       | null  | "DE2-Desc"  | false  | "Blank DataElementName is not accepted."
		null        | null  | "DE2-Desc"  | false  | "Blank DataElementName is not accepted."
		null        | "DE1" | "DE2-Desc"  | false  | "Blank DataElementUniqueCode is not accepted."
		""          | "DE1" | "DE2-Desc"  | false  | "Blank DataElementUniqueCode is not accepted."
	}


	def "addValueDomain adds a new ValueDomain"() {

		given: "A conceptualDomain,DataType and MeasurementUnit already exists"
		def conDomain = new ConceptualDomain(name: "NHIC", description: "NHIC conceptual domain", catalogueId: "1", catalogueVersion: "1").save(flush: true, failOnError: true)
		def dataType = new DataType(enumerated: false, name: "d", catalogueId: "d", catalogueVersion: "d").save(flush: true, failOnError: true)
		def mUnit = new MeasurementUnit(name: "centimeter", symbol: "cm").save(flush: true)

		when: "addValueDomain is called"
		def vdCountBefore = ValueDomain.count()
		def valueDomainResult = service.addValueDomain(vdName, "Test-Desc", dataType, measurementUnit, conDomain)

		then: "a new valueDomain is added or invalid vlueDomain is rejected"
		valueDomainResult[0] == result
		ValueDomain.count() == vdCountBefore + addedValueDomin

		where: ""
		vdName | addedValueDomin | result | measurementUnit				|	message
		"vd1"  | 1               | true   | MeasurementUnit.list()[0]	|	""
		"vd1"  | 1               | true   | null						|	""
		""     | 0               | false  |	MeasurementUnit.list()[0]	| "Blank DataElementName is not accepted."
		null   | 0               | false  | MeasurementUnit.list()[0]	| "Blank DataElementName is not accepted."

	}


	def "matchOrAddMeasurementUnit match existing MeasurementUnit"() {

		given: "A measurementUnit already exists"
		MeasurementUnit expected = new MeasurementUnit(name: "centimeter", symbol: "cm").save(flush: true)

		when: "matchOrAddMeasurementUnit is called"
		def result = service.matchOrAddMeasurementUnit("centimeter", "cm")

		then: "it returns the available measurementUnit"
		result[1].id == expected.id
	}


	def "matchOrAddMeasurementUnit creates a new one for not available MeasurementUnit"() {

		given: "MeasurementUnit does not exist"
		MeasurementUnit.countByName("centimeter") == 0

		when: "matchOrAddMeasurementUnit is called"
		def result = service.matchOrAddMeasurementUnit("centimeter", "cm")

		then: "it creates a new measurementUnit"
		MeasurementUnit.count() == 1
		result[0] == true
		MeasurementUnit.list()[0].id == result[1].id
	}


	def "matchOrAddMeasurementUnit returns null if measurementUnitName is empty or null"(){
		given: "MeasurementUnit does not exist"
		MeasurementUnit.countByName("centimeter") == 0

		when: "matchOrAddMeasurementUnit is called will empty/null name"
		def result = service.matchOrAddMeasurementUnit(name, "")

		then: "it returns a null value"
		MeasurementUnit.count() == 0
		result[0] == true
		result[1] == null

		where:
		name << ["", null]
	}


	def "deleteOldConceptualDomain will delete available conceptualDomain"() {

		given: "A conceptualDomain exists"
		def conceptualDomain = new ConceptualDomain(name: "NHIC", description: "NHIC conceptual domain", catalogueId: "1", catalogueVersion: "1").save(flush: true, failOnError: true)

		when: "deleteOldConceptualDomain is called"
		def result = service.deleteOldConceptualDomain(conceptualDomain)

		then: "the conceptualDomain will be removed"
		1 * service.conceptualDomainService.delete(conceptualDomain) >> {}
		result[0] == true
		result[1] == null
		result[2] == ""
	}

	def "deleteOldConceptualDomain will return appropriate message if fails"() {

		given: ""
		def con = new ConceptualDomain(name: "nhic", description: "test")

		when: "deleteOldConceptualDomain is called"
		def result = service.deleteOldConceptualDomain(con)

		then: "the conceptualDomain will be removed"
		1 * service.conceptualDomainService.delete(con) >> { throw new Exception("Error in delete!!!") }
		result[0] == false
		result[1] == null
		result[2] == "Can not delete conceptualDomain nhic , Error:Error in delete!!!"
	}


	@Unroll
	def "ingestRow adds a new row and returns successful result for #fixtID"() {

		given: ""
		ConceptualDomain conceptualDomain = new ConceptualDomain(name: "new-Domain").save(failOnError: true, flush: true)

		when: ""
		def importRow = new ImportRow(
				dataElementName: deName,
				dataElementCode: deCode,
				parentModelName: pmName,
				parentModelCode: pmCode,
				containingModelName: mName,
				containingModelCode: mCode,
				dataType: dt,
				dataElementDescription: "",
				measurementUnitName: mUnitName,
				measurementSymbol: mUnitSymbol,
				conceptualDomainName: "",
				conceptualDomainDescription: "",
				metadata: metadata)

		def deCountBefore = DataElement.count()

		service.ingestRow(importRow, conceptualDomain)

		then: ""
		1 * service.dataElementValueDomainService.link(_ as DataElement, _ as ValueDomain) >> {}
		importRow.status == status
		importRow.statusMessage == message
		DataElement.count() == deCountBefore + 1
		DataElement.list()[0].name == deName
		DataElement.list()[0].catalogueId == deCode
		DataElement.list()[0]?.model?.name == (mName == "" ? pmName : mName)
		DataElement.list()[0]?.extensions == metadata

		where:
		fixtID | deName | deCode  | pmName   | pmCode | mName   | mCode  | dt       | mUnitName | mUnitSymbol | metadata        | status | message
		1      | "DE"   | "DE123" | "parent" | "p123" | "Model" | "m123" | "string" | "meter"   | "m"         | ["code": "123"] | true   | "Successful"
		2      | "DE"   | "DE123" | "parent" | "p123" | ""      | ""     | "string" | "meter"   | "m"         | ["code": "123"] | true   | "Successful"
		3      | "DE"   | "DE123" | ""       | ""     | "Model" | "m123" | "string" | "meter"   | "m"         | ["code": "123"] | true   | "Successful"

	}


	@Unroll
	def "ingestRow fails in adding a new row and returns error message for #fixtID"() {

		given: "a ConceptualDomain already exists"
		ConceptualDomain conceptualDomain = new ConceptualDomain(name: "new-Domain").save(failOnError: true, flush: true)

		when: "ingestRow is called"
		def importRow = new ImportRow(
				dataElementName: deName,
				dataElementCode: deCode,
				parentModelName: pmName,
				parentModelCode: pmCode,
				containingModelName: mName,
				containingModelCode: mCode,
				dataType: dt,
				dataElementDescription: "",
				measurementUnitName: mUnitName,
				measurementSymbol: mUnitSymbol,
				conceptualDomainName: "",
				conceptualDomainDescription: "",
				metadata: metadata);

		def deCountBefore = DataElement.count()

		service.ingestRow(importRow, conceptualDomain)

		then: "it fails in this scenario"
		importRow.status == status
		importRow.statusMessage == message
		DataElement.count() == deCountBefore

		where:
		fixtID | deName | deCode  | pmName   | pmCode | mName   | mCode  | dt       | mUnitName | mUnitSymbol | metadata        | status | message
		1      | ""     | ""      | "parent" | "p123" | "Model" | "m123" | "string" | "meter"   | "m"         | ["code": "123"] | false  | "Blank DataElementName is not accepted."
		2      | "DE"   | "DE123" | ""       | ""     | ""      | ""     | "string" | "meter"   | "m"         | ["code": "123"] | false  | "Parent Model Code or Model Code should be defined."
		3      | "DE"   | "DE123" | ""       | ""     | "Model" | "m123" | ""       | "meter"   | "m"         | ["code": "123"] | false  | "Blank DataType is not accepted."

	}


	def "importData will return appropriate message in case of invalid headers"() {

		given: "an excelHeader is available"
		def excelInputHeaders = [
//			"Data Item Unique Code",
				"Data Item Name",
				"Data Item Description",
				"Data type",
				"Parent Model",
				"Parent Model Unique Code",
				"Model",
				"Model Unique Code",
				"Measurement Unit",
//			"Metadata"
		];

		when: "importData is called"
		def result = service.importData(excelInputHeaders, [], "", "")

		then: "it will return error message containing missed headers"
		result[0] == false
		result[1] == []
		result[2] == "Data Item Unique Code, Metadata"
	}

	def "importData will return appropriate message when can not delete old conceptualDomain"() {

		given: "A conceptualDomain exists"
		ConceptualDomain conceptualDomain = new ConceptualDomain(name: "cd1").save(failOnError: true, flush: true)

		when: "importData is called"
		def conCountBefore = ConceptualDomain.count()
		def headers = service.getHeaders().getHeadersStringValue()
		def result = service.importData(headers, [], "cd1", "")

		then: "conceptualDomain deletion fails and importData roll-backs"
		1 * service.conceptualDomainService.delete(_) >> { throw new Exception("Error in delete!!!") }
		result[0] == false
		ConceptualDomain.count() == conCountBefore
		result[0] == false
		result[1] == []
		result[2] == "Can not delete conceptualDomain cd1 , Error:Error in delete!!!"
	}


	def "importData will import the rows"(){
		given: "A conceptualDomain exists"
		new ConceptualDomain(name: "cd1").save(failOnError: true, flush: true)

		when: "importData is called"
		def headers = service.getHeaders().getHeadersStringValue()
		def rows =
				[
						[
							"DE1", "DE","Desc","string","parent-Model-Name","parent-Model-Code",
								"Model-name","Model-Code","measurement","metadata","MetaData1"
						],
						[  "DE2", "DE","Desc","string","parent-Model-Name","parent-Model-Code",
								"Model-name","Model-Code","measurement","metadata","MetaData2"
						]
				]

		def deCountBefore  = DataElement.count()
		def modelCountBefore  = Model.count()

		def result = service.importData(headers, rows, "cd1", "cd-Desc")

		then:"importData imports the rows properly"
		1 * service.conceptualDomainService.delete(_) >> { }
		result[0] == true
		DataElement.count() == deCountBefore + 2
		Model.count() == modelCountBefore + 2 //just adds two models as they are added in the first row
	}

}