package uk.co.brc.mcv.marshaller

import grails.converters.JSON
import uk.co.mcv.model.MeasurementUnit

/**
 * Created by soheil on 09/05/2014.
 */
class MeasurementUnitMarshaller {

	void register() {
		JSON.registerObjectMarshaller( MeasurementUnit) { measurementUnit ->
			return [
					id : measurementUnit.id,
					name : measurementUnit.name,
					symbol: measurementUnit.symbol
			]
		}
	}
}
