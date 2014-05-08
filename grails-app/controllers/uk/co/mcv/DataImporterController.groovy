package uk.co.mcv

import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest
import uk.co.mcv.importer.ExcelLoader
import uk.co.mcv.importer.HeadersMap

class DataImporterController {

	def dataImportService

	def index() {}

	def upload()
	{
		if(!(request instanceof MultipartHttpServletRequest))
		{
			flash.error="No File to process!"
			render view:"index"
			return
		}

		ArrayList parentModels
		String conceptualDomainName, conceptualDomainDescription

		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request
		MultipartFile  file = multiRequest.getFile("excelFile")

		def params = multiRequest.multipartParameters
		if(!params.conceptualDomainName){
			flash.error="No Conceptual Domain Name"
			render view:"index"
			return
		}else{
			conceptualDomainName = params.conceptualDomainName.toString().replaceAll('\\[', "").replaceAll('\\]', "").trim()
		}
		if(params.conceptualDomainDescription){
			conceptualDomainDescription = params.conceptualDomainDescription.toString().replaceAll('\\[', "").replaceAll('\\]', "").trim()
		}else{
			conceptualDomainDescription=""
		}

		if(params.parentModels){
			parentModels  = params.parentModels.toString().replaceAll('\\[', "").replaceAll('\\]', "").trim().split(',')
		}else{
			params.parentModels=""}

		//Microsoft Excel files
		//Microsoft Excel 2007 files
		def okContentTypes = ['application/vnd.ms-excel','application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', 'application/octet-stream']
		def confType=file.getContentType()
		if (okContentTypes.contains(confType) && file.size > 0){
			try {
				ExcelLoader parser = new ExcelLoader(file.inputStream)
				def (headers, rows) = parser.parse()

				dataImportService.importData(headers, rows, conceptualDomainName, conceptualDomainDescription, parentModels )

				//if (result) {
				flash.message = "DataElements have been created.\n"
				//}
			}
			catch(Exception ex)
			{
				//log.error("Exception in handling excel file: "+ ex.message)
				log.error("Exception in handling excel file")
				flash.message ="Error in importing the excel file.";
			}
		}
		else
		{
			if(!okContentTypes.contains(confType))
				flash.message ="Input should be an Excel file!\n"+
						"but uploaded content is "+confType
			else if (file.size<=0)
				flash.message ="The uploaded file is empty!"
		}

		render view: 'index'
	}
}
