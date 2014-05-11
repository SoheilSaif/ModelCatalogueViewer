package uk.co.mcv.importer

/**
 * Created by soheil on 07/05/2014.
 */
class ImportRow {
	String dataElementCode
	String dataElementName
	String dataElementDescription
	String conceptualDomainName
	String conceptualDomainDescription
	String dataType
	String parentModelName
	String parentModelCode
	String containingModelName
	String containingModelCode
	String measurementUnitName
	String measurementSymbol
	Map metadata

	Boolean status
	String statusMessage
}