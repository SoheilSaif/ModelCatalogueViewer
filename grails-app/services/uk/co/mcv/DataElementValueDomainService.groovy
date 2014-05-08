package uk.co.mcv

import grails.transaction.Transactional
import uk.co.mcv.model.DataElement
import uk.co.mcv.model.DataElementValueDomain
import uk.co.mcv.model.ValueDomain

@Transactional
class DataElementValueDomainService {

	DataElementValueDomain link(DataElement, ValueDomain) {
		def m = DataElementValueDomain.findByDataElementAndValueDomain(DataElement, ValueDomain)
		if (!m) {
			m = new DataElementValueDomain()
			DataElement?.addToDataElementValueDomains(m)
			ValueDomain?.addToDataElementValueDomains(m)
			m.save()
		}
		return m
	}

	void unlink(DataElementValueDomain dv) {
		dv?.dataElement.removeFromDataElementValueDomains(dv)
		dv?.valueDomain.removeFromDataElementValueDomains(dv)
		dv.delete()
	}
}
