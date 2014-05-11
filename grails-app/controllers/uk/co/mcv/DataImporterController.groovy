package uk.co.mcv

import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest
import uk.co.mcv.importer.ExcelLoader
import uk.co.mcv.importer.HeadersMap

class DataImporterController {

	def dataImportService

	def index() {}

	def upload() {
		if (!(request instanceof MultipartHttpServletRequest)) {
			flash.error = "No File to process!"
			render view: "index"
			return
		}

		String conceptualDomainName, conceptualDomainDescription

		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request
		MultipartFile file = multiRequest.getFile("excelFile")

		def params = multiRequest.multipartParameters
		if (!params.conceptualDomainName) {
			flash.error = "No Conceptual Domain Name"
			render view: "index"
			return
		} else {
			conceptualDomainName = params.conceptualDomainName.toString().replaceAll('\\[', "").replaceAll('\\]', "").trim()
		}
		if (params.conceptualDomainDescription) {
			conceptualDomainDescription = params.conceptualDomainDescription.toString().replaceAll('\\[', "").replaceAll('\\]', "").trim()
		} else {
			conceptualDomainDescription = ""
		}

		//Microsoft Excel files
		//Microsoft Excel 2007 files
		def okContentTypes = ['application/vnd.ms-excel', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', 'application/octet-stream']
		def confType = file.getContentType()
		if (okContentTypes.contains(confType) && file.size > 0) {
			try {
				ExcelLoader parser = new ExcelLoader(file.inputStream)
				def (headers, rows) = parser.parse()

				def result = dataImportService.importData(headers, rows, conceptualDomainName, conceptualDomainDescription)

				if (result[0]) {
					flash.message = "DataElements have been created.\n"
				} else {
					flash.message = "Error in processing DataElements.\n"
					flash.processError = true
					render view:'index',model:[rows:result[1]]
					return
				}
			}
			catch (Exception ex) {
				//log.error("Exception in handling excel file: "+ ex.message)
				log.error("Exception in handling excel file")
				flash.message = "Error in importing the excel file." + ex.message;
			}
		} else {
			if (!okContentTypes.contains(confType))
				flash.message = "Input should be an Excel file!\n" +
						"but uploaded content is " + confType
			else if (file.size <= 0)
				flash.message = "The uploaded file is empty!"
		}

		render view: 'index'
	}
}
