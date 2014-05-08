package uk.co.mcv

import grails.transaction.Transactional
import uk.co.mcv.importer.HeadersMap
import uk.co.mcv.importer.ImportRow
import uk.co.mcv.importer.Importer
import uk.co.mcv.model.ConceptualDomain
import uk.co.mcv.model.DataElement
import uk.co.mcv.model.DataType
import uk.co.mcv.model.Model
import uk.co.mcv.model.ValueDomain

class DataImportService {

	def sessionFactory
	static transactional = true

	//the import script accepts and array of headers these should include the following:
	//Data Item Name, Data Item Description, Parent Section, Section, Measurement Unit, Data type
	//these will allow the import script to identify the rows

	def importData(ArrayList headers, ArrayList rows, String conceptualDomainName, String conceptualDomainDescription) {

		//final import result, contains result which is true/false and importedRows all importedRows and message
		//like result = [true,[{},{},{}],"error in"]
		def result = []

		//get the actual expected headers
		HeadersMap headersMap = getHeaders();

		//check if input header, has all columns that we expect
		//headerValidation is a two item array [0] show status and [1] shows error messages
		def headerValidation = validateHeaders(headers)
		if (!headerValidation[0]) {
			return [false, [], headerValidation[1]]
		}

		//get indexes of the appropriate sections
		def totalCounter = 0
		parentModels.add(0, "ModelCatalogue")

		def newImporter = new Importer(parentModels: parentModels)

		def dataItemNameIndex = headers.indexOf(headersMap.dataElementNameRow)
		def dataItemCodeIndex = headers.indexOf(headersMap.dataElementCodeRow)
		def dataItemDescriptionIndex = headers.indexOf(headersMap.dataElementDescriptionRow)
		def parentModelIndex = headers.indexOf(headersMap.parentModelNameRow)
		def modelIndex = headers.indexOf(headersMap.containingModelNameRow)
		def parentModelCodeIndex = headers.indexOf(headersMap.parentModelCodeRow)
		def modelCodeIndex = headers.indexOf(headersMap.containingModelCodeRow)
		def unitsIndex = headers.indexOf(headersMap.measurementUnitNameRow)
		def dataTypeIndex = headers.indexOf(headersMap.dataTypeRow)

		def metadataStartIndex = headers.indexOf(headersMap.metadataRow) + 1
		def metadataEndIndex = headers.size() - 1

		def elements = []
		if (dataItemNameIndex == -1)
			throw new Exception("Can not find 'Data Item Name' column")


		def conceptualDomain = addConceptualDomain(conceptualDomainName, conceptualDomainDescription)

		//iterate through the rows and import each line
		rows.eachWithIndex { def row, int i ->

			ImportRow importRow = new ImportRow()

			importRow.dataElementName = (dataItemNameIndex != -1) ? row[dataItemNameIndex] : null
			importRow.dataElementCode = (dataItemCodeIndex != -1) ? row[dataItemCodeIndex] : null
			importRow.parentModelName = (parentModelIndex != -1) ? row[parentModelIndex] : null
			importRow.parentModelCode = (parentModelCodeIndex != -1) ? row[parentModelCodeIndex] : null
			importRow.containingModelName = (modelIndex != -1) ? row[modelIndex] : null
			importRow.containingModelCode = (modelCodeIndex != -1) ? row[modelCodeIndex] : null
			importRow.dataType = (dataTypeIndex != -1) ? row[dataTypeIndex] : null
			importRow.dataElementDescription = (dataItemDescriptionIndex != -1) ? row[dataItemDescriptionIndex] : null
			importRow.measurementUnitName = (unitsIndex != -1) ? row[unitsIndex] : null

			importRow.conceptualDomainName = conceptualDomainName
			importRow.conceptualDomainDescription = conceptualDomainDescription

			importRow.parentModelCode = (parentModelCodeIndex != -1) ? row[parentModelCodeIndex] : null

			def counter = metadataStartIndex
			def metadataColumns = [:]
			while (counter <= metadataEndIndex) {
				metadataColumns.put(headers[counter], row[counter])
				counter++
			}
			importRow.metadata = (metadataColumns) ? metadataColumns : null

			if (totalCounter > 40) {
				sessionFactory.currentSession.flush()
				sessionFactory.currentSession.clear()
				counter = 0
			} else {
				totalCounter++
			}

			newImporter.ingestRow(importRow)
		}

		newImporter.actionPendingModels()
		sessionFactory.currentSession.flush()
		sessionFactory.currentSession.clear()
	}


	def ingestRow(ImportRow row, ConceptualDomain conceptualDomain) {


		def dataTypeResult = matchOrAddDataType(row.dataElementName, row.dataType)
		//has some error
		if (!dataTypeResult[0]) {
			row.status = false
			row.statusMessage = dataTypeResult[2] //get error message
			return
		}

		def modelResult = addModel(row.parentModelCode, row.parentModelName, row.containingModelCode, row.containingModelName, conceptualDomain)
		//has some error
		if(!modelResult[0]){
			row.status = false
			row.statusMessage = modelResult[2]// get error message
		}

		//def measurementUnit = matchOrAddMeasurementUnit(row.measurementUnitName, row.measurementSymbol)
		def dataElement = addDataElement(row.dataElementName, row.dataElementDescription, row.dataElementCode, row.metadata, model, dataType,row.measurementUnitName, row.measurementSymbol)

	}


	def addDataElement(name, description, catalogueId, metadata, Model model, DataType dataType,unitOfMeasure) {

//		ValueDomain valueDomain = new ValueDomain()
//
//		DataElement dataElement = new DataElement(name:name,description:description,catalogueId:catalogueId)
//		model.addToDataElements(dataElement)



	}

	def matchOrAddMeasurementUnit(name, symbol) {

	}

	def addModel(String parentCode, String parentName,String  modelCode,String  modelName, ConceptualDomain conceptualDomain) {

		Model model = Model.findByConceptualDomainAndCatalogueId(conceptualDomain, modelCode)
		Model parentModel = Model.findByConceptualDomainAndCatalogueId(conceptualDomain, parentCode)

		//parent is Not empty and model is empty
		if(!parentName.isEmpty() && modelName.isEmpty()){
			if(parentModel)
				return [true,parentModel,""]
			model = new Model(name:parentName ,catalogueId: parentCode)
			conceptualDomain.addToModels(model)
			model.save(failOnError: true)
			return [true,model,""]
		}

		//parent is empty and model is Not
		if(parentName.isEmpty() && !modelName.isEmpty()){
			if(model)
				return [true,model,""]
			model = new Model(name:modelName ,catalogueId: modelCode)
			conceptualDomain.addToModels(model)
			model.save(failOnError: true)
			return [true,model,""]
		}


		//parent is Not empty and model is Not empty
		if(!parentName.isEmpty() && !modelName.isEmpty()){

			//they both exists
			if(parentModel && model){
				//ERROR, model has a different parent, can not change parent of an existing model
				if (model.parentModel?.id != parentModel?.id){
					return [false,null,"Model has a different parent! can not change model parent."]
				}else{
					return [true,model,""]
				}
			}

			//they both do NOT exist
			if(!parentModel && !model){

				parentModel = new Model(name:parentName,catalogueId: parentCode)
				conceptualDomain.addToModels(parentModel)
				parentModel.save(failOnError: true,flush: true)

				model = new Model(name:modelName ,catalogueId: modelCode)
				parentModel.addToSubModels(model)
				conceptualDomain.addToModels(model)
				model.save(failOnError: true)
				return [true,model,""]
			}

			//parent exists, but model does NOT
			if(parentModel && !model){
				model = new Model(name:modelName ,catalogueId: modelCode)
				parentModel.addToSubModels(model)
				conceptualDomain.addToModels(model)
				model.save(failOnError: true)
				return [true,model,""]
			}

			//parent does not exist but model exists !!
			if(!parentModel && model){
				//ERROR, can not change parent of an existing model
				if (model.parentModel){
					return [false,null,"Model has a different parent! can not change model parent."]
				}else{ //model does not have parent, so add this parent and update the model
					parentModel = new Model(name:parentName,catalogueId: parentCode)
					conceptualDomain.addToModels(parentModel)
					parentModel.addToSubModels(model)
					parentModel.save(failOnError: true,flush: true)
					return [true,model,""]
				}
			}
		}

		//if parentName and modelName are both empty,
		//it is rejected, as least one should be defined
		if(parentName.isEmpty() && modelName.isEmpty()){
			return [false,null,"Parent Model Code or Model Code should be defined."]
		}
	}

	def addConceptualDomain(name, description) {
		new ConceptualDomain(name: name, description: description, catalogueId: "", catalogueVersion: "").save(failOnError: true, flush: true)
	}

	def matchOrAddDataType(String DataElementName, String dataTypeStringValue) {

		DataType dataType
		Map enums = [:]
		boolean isEnum = false

		//if DataType already exists, return it
		dataType = DataType.findByNameIlike(DataElementName)
		if (dataType) {
			return [true, dataType]
		} else
			dataType = new DataType();

		//it is an enumerated DataType as contains "|"
		if (dataTypeStringValue.contains("|")) {
			def lines = dataTypeStringValue.split("\\|")

			boolean isValid = true
			lines.each { line ->
				String[] keyValue = line.split(":")
				//it should contain exactly one : like "1:male"
				if (keyValue.size() == 2) {
					isEnum = true
					enums.put(keyValue[0], keyValue[1])
				} else {
					isValid = false
				}
			}
			if (!isValid)
				return [false, null, "Invalid format for DataType enum"];

			dataType.name = DataElementName
		}//it is an enumerated DataType as contains ";"
		else if (dataTypeStringValue.contains(";")) {
			def lines = dataTypeStringValue.split(";")

			boolean isValid = true
			lines.each { line ->
				String[] keyValue = line.split(":")
				//it should contain exactly one : like "1:male"
				if (keyValue.size() == 2) {
					isEnum = true
					enums.put(keyValue[0], keyValue[1])
				} else {
					isValid = false
				}
			}
			if (!isValid)
				return [false, null, "Invalid format for DataType enum"];

			dataType.name = DataElementName
		}
		//just have one ":", so it is not enum, it is an XML type like xsd:string
		else if (dataTypeStringValue.lastIndexOf(":") == dataTypeStringValue.indexOf(":")) {
			dataType.name = dataTypeStringValue
		}
		//has no '|' and has no single ':' , it should be a simple text dataType
		else {
			dataType.name = dataTypeStringValue
		}
		//if dataType property is empty, use dataElement name
		if (dataType.name.isEmpty())
			dataType.name = DataElementName

		dataType.enumerated = isEnum
		dataType.enumerations = enums
		dataType.save(failOnError: true)

		return [true, dataType, ""]
	}


	HeadersMap getHeaders() {
		def headersMap = new HeadersMap()
		headersMap.dataElementCodeRow = "Data Item Unique Code"
		headersMap.dataElementNameRow = "Data Item Name"
		headersMap.dataElementDescriptionRow = "Data Item Description"
		headersMap.dataTypeRow = "Data type"
		headersMap.parentModelNameRow = "Parent Model"
		headersMap.parentModelCodeRow = "Parent Model Unique Code"
		headersMap.containingModelNameRow = "Model"
		headersMap.containingModelCodeRow = "Model Unique Code"
		headersMap.measurementUnitNameRow = "Measurement Unit"
		headersMap.metadataRow = "Metadata"
		return headersMap
	}

	def validateHeaders(ArrayList<String> inputHeaders) {

		boolean result = true
		StringBuilder message = new StringBuilder("")
		HeadersMap headersMap = getHeaders()
		def actualHeaders = headersMap.getHeadersStringValue()

		actualHeaders.each { header ->
			if (!inputHeaders.contains(header)) {
				result = false
				message.append(header + ", ")
			}
		}

		def messageString = ""
		if (!result)
			messageString = message.substring(0, message.lastIndexOf(", "))

		return [result, messageString]
	}
}