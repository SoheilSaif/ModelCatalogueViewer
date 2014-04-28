package uk.co.mcv

import uk.co.mcv.model.ConceptualDomain

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DataElementLocalController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ConceptualDomain.DataElementLocal.list(params), model:[dataElementLocalInstanceCount: ConceptualDomain.DataElementLocal.count()]
    }

    def show(ConceptualDomain.DataElementLocal dataElementLocalInstance) {
        respond dataElementLocalInstance
    }

    def create() {
        respond new ConceptualDomain.DataElementLocal(params)
    }

    @Transactional
    def save(ConceptualDomain.DataElementLocal dataElementLocalInstance) {
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

    def edit(ConceptualDomain.DataElementLocal dataElementLocalInstance) {
        respond dataElementLocalInstance
    }

    @Transactional
    def update(ConceptualDomain.DataElementLocal dataElementLocalInstance) {
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
    def delete(ConceptualDomain.DataElementLocal dataElementLocalInstance) {

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
