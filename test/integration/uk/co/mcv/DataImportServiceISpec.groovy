package uk.co.mcv

import grails.test.mixin.TestFor
import grails.test.spock.IntegrationSpec
import spock.lang.Unroll
import uk.co.mcv.model.ConceptualDomain
import uk.co.mcv.model.DataElement
import uk.co.mcv.model.DataElementValueDomain
import uk.co.mcv.model.DataType
import uk.co.mcv.model.MeasurementUnit
import uk.co.mcv.model.Model
import uk.co.mcv.model.ValueDomain

/**
 * Created by soheil on 11/05/2014.
 */
class DataImportServiceISpec extends IntegrationSpec {

	static transactional = false
	def dataImportService

	@Unroll
	def "importData will roll-back all in case of error for #fixId"() {

		given: "A conceptualDomain exists"
		def cd = new ConceptualDomain(name: "cd1").save(failOnError: true, flush: true)

		when: "importData is called"
		def headers = dataImportService.getHeaders().getHeadersStringValue()
		def result = dataImportService.importData(headers, rows, "cd1", "cd-Desc")

		then: "importData will roll back"
		result[0] == false
		result[1].size() == rows.size()
		(result[1])[2].statusMessage == message
		ConceptualDomain.count() == 1
		DataElement.count() == 0
		Model.count() == 0
		DataType.count() == 0
		MeasurementUnit.count() == 0
		ValueDomain.count() == 0
		DataElementValueDomain.count() == 0

		cleanup:"remove created conceptualDomain"
		cd.delete()

		where: ""
		fixId	| rows							| message
		1		| haveBlankDataElementName()	|	"Blank DataElementName is not accepted."
		2		| haveBlankDataElementCode()	|	"Blank DataElementUniqueCode is not accepted."
		3		| haveIncorrectModel()			|	"Parent Model Code or Model Code should be defined."
		4		| haveIncorrectModelParent()	|	"Model has a different parent! you can not change parent model."
		5		| haveBlankDataType()			|	"Blank DataType is not accepted."


	}

	//generate empty dataElementName
	private def haveBlankDataElementName() {
		[
			[
			 "DE1", "DE", "Desc", "string", "parent-Model-Name", "parent-Model-Code",
			 "Model-name", "Model-Code", "measurement", "metadata", "MetaData1"
			],
			["DE2", "DE", "Desc", "string", "parent-Model-Name", "parent-Model-Code",
			 "Model-name", "Model-Code", "measurement", "metadata", "MetaData2"
			],
			["DE3", "", "Desc", "string", "parent-Model-Name", "parent-Model-Code",
			 "Model-name", "Model-Code", "measurement", "metadata", "MetaData2"
			]
		]
	}


	//generate empty dataElementName
	private def haveBlankDataElementCode() {
		[
				[
				"DE1", "DE", "Desc", "string", "parent-Model-Name", "parent-Model-Code",
				"Model-name", "Model-Code", "measurement", "metadata", "MetaData1"
				],
				["DE2", "DE", "Desc", "string", "parent-Model-Name", "parent-Model-Code",
				"Model-name", "Model-Code", "measurement", "metadata", "MetaData2"
				],
				["", "De3", "Desc", "string", "parent-Model-Name", "parent-Model-Code",
				"Model-name", "Model-Code", "measurement", "metadata", "MetaData2"
				]
		]
	}

	//generate empty parentModel and mainModel
	private def haveIncorrectModel() {
		[
				[
				"DE1", "DE", "Desc", "string", "parent-Model-Name", "parent-Model-Code",
				"Model-name", "Model-Code", "measurement", "metadata", "MetaData1"
				],
				["DE2", "DE", "Desc", "string", "parent-Model-Name", "parent-Model-Code",
				 "Model-name", "Model-Code", "measurement", "metadata", "MetaData2"
				],
				["DE3", "DE3", "Desc", "string", "", "",
				 "", "", "measurement", "metadata", "MetaData2"
				]
		]
	}


		//generate empty parentModel and mainModel
		private def haveIncorrectModelParent() {
			[
					[
							"DE1", "DE", "Desc", "string", "parent-Model-Name", "parent-Model-Code",
							"Model-name", "Model-Code", "measurement", "metadata", "MetaData1"
					],
					["DE2", "DE", "Desc", "string", "parent-Model-Name", "parent-Model-Code",
							"Model-name", "Model-Code", "measurement", "metadata", "MetaData2"
					],
					["DE2", "DE", "Desc", "string", "parent-Model-Name-ODD", "parent-Model-Code-ODD",
							"Model-name", "Model-Code", "measurement", "metadata", "MetaData2"
					],

			]

		}



	//generate empty dataType
	private def haveBlankDataType() {
		[
				[
				"DE1", "DE", "Desc", "string", "parent-Model-Name", "parent-Model-Code",
				"Model-name", "Model-Code", "measurement", "metadata", "MetaData1"
				],
				["DE2", "DE", "Desc", "string", "parent-Model-Name", "parent-Model-Code",
				 "Model-name", "Model-Code", "measurement", "metadata", "MetaData2"
				],
				["De3", "De3", "Desc", "", "parent-Model-Name", "parent-Model-Code",
				"Model-name", "Model-Code", "measurement", "metadata", "MetaData2"
				]
		]
	}

}

