import uk.co.brc.mcv.marshaller.ConceptualDomainMarshaller
import uk.co.brc.mcv.marshaller.CustomObjectMarshallers
import uk.co.brc.mcv.marshaller.DataElementMarshaller
import uk.co.brc.mcv.marshaller.DataTypeMarshaller
import uk.co.brc.mcv.marshaller.LinkMarshaller
import uk.co.brc.mcv.marshaller.MeasurementUnitMarshaller
import uk.co.brc.mcv.marshaller.ModelMarshaller
import uk.co.brc.mcv.marshaller.NodeMarshaller
import uk.co.brc.mcv.marshaller.PathwayMarshaller
import uk.co.brc.mcv.marshaller.ValueDomainMarshaller
import uk.co.mcv.model.ConceptualDomain
import uk.co.mcv.model.DataType
import uk.co.mcv.model.ValueDomain

// Place your Spring DSL code here
beans = {
    customObjectMarshallers( CustomObjectMarshallers ) {
        marshallers = [
                new ModelMarshaller(),
				new DataElementMarshaller(),
				new DataTypeMarshaller(),
				new ValueDomainMarshaller(),
				new PathwayMarshaller(),
				new LinkMarshaller(),
				new NodeMarshaller(),
				new ConceptualDomainMarshaller(),
				new MeasurementUnitMarshaller()
        ]
    }
}