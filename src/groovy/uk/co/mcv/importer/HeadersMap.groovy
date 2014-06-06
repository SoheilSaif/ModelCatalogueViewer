package uk.co.mcv.importer

/**
 * Created by soheil on 07/05/2014.
 */
class HeadersMap {
	String dataElementCodeRow
	String dataElementNameRow
	String dataElementDescriptionRow
	String dataTypeRow
	String parentModelNameRow
	String parentModelCodeRow
	String containingModelNameRow
	String containingModelCodeRow
	String measurementUnitNameRow
	String metadataRow


	def getHeadersStringValue(){
		def headers = []
		headers.add(dataElementCodeRow)
		headers.add(dataElementNameRow)
		headers.add(dataElementDescriptionRow)
		headers.add(dataTypeRow)
		headers.add(parentModelNameRow)
		headers.add(parentModelCodeRow)
		headers.add(containingModelNameRow)
		headers.add(containingModelCodeRow)
		headers.add(measurementUnitNameRow)
		headers.add(metadataRow)
		return  headers
	}
}