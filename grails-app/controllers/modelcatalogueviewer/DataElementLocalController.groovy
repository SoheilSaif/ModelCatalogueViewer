package modelcatalogueviewer


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DataElementLocalController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond DataElementLocal.list(params), model:[dataElementLocalInstanceCount: DataElementLocal.count()]
    }

    def show(DataElementLocal dataElementLocalInstance) {
        respond dataElementLocalInstance
    }

    def create() {
        respond new DataElementLocal(params)
    }

    @Transactional
    def save(DataElementLocal dataElementLocalInstance) {
        if (dataElementLocalInstance == null) {
            notFound()
            return
        }

        if (dataElementLocalInstance.hasErrors()) {
            respond dataElementLocalInstance.errors, view:'create'
            return
        }

        dataElementLocalInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'dataElementLocalInstance.label', default: 'DataElementLocal'), dataElementLocalInstance.id])
                redirect dataElementLocalInstance
            }
            '*' { respond dataElementLocalInstance, [status: CREATED] }
        }
    }

    def edit(DataElementLocal dataElementLocalInstance) {
        respond dataElementLocalInstance
    }

    @Transactional
    def update(DataElementLocal dataElementLocalInstance) {
        if (dataElementLocalInstance == null) {
            notFound()
            return
        }

        if (dataElementLocalInstance.hasErrors()) {
            respond dataElementLocalInstance.errors, view:'edit'
            return
        }

        dataElementLocalInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'DataElementLocal.label', default: 'DataElementLocal'), dataElementLocalInstance.id])
                redirect dataElementLocalInstance
            }
            '*'{ respond dataElementLocalInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(DataElementLocal dataElementLocalInstance) {

        if (dataElementLocalInstance == null) {
            notFound()
            return
        }

        dataElementLocalInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'DataElementLocal.label', default: 'DataElementLocal'), dataElementLocalInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElementLocalInstance.label', default: 'DataElementLocal'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
