package modelcatalogueviewer



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DataElementController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond DataElement.list(params), model:[dataElementInstanceCount: DataElement.count()]
    }

    def show(DataElement dataElementInstance) {
        respond dataElementInstance
    }

    def create() {
        respond new DataElement(params)
    }

    @Transactional
    def save(DataElement dataElementInstance) {
        if (dataElementInstance == null) {
            notFound()
            return
        }

        if (dataElementInstance.hasErrors()) {
            respond dataElementInstance.errors, view:'create'
            return
        }

        dataElementInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'dataElementInstance.label', default: 'DataElement'), dataElementInstance.id])
                redirect dataElementInstance
            }
            '*' { respond dataElementInstance, [status: CREATED] }
        }
    }

    def edit(DataElement dataElementInstance) {
        respond dataElementInstance
    }

    @Transactional
    def update(DataElement dataElementInstance) {
        if (dataElementInstance == null) {
            notFound()
            return
        }

        if (dataElementInstance.hasErrors()) {
            respond dataElementInstance.errors, view:'edit'
            return
        }

        dataElementInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'DataElement.label', default: 'DataElement'), dataElementInstance.id])
                redirect dataElementInstance
            }
            '*'{ respond dataElementInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(DataElement dataElementInstance) {

        if (dataElementInstance == null) {
            notFound()
            return
        }

        dataElementInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'DataElement.label', default: 'DataElement'), dataElementInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElementInstance.label', default: 'DataElement'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
