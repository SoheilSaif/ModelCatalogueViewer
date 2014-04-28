import uk.co.brc.mcv.marshaller.CustomObjectMarshallers
import uk.co.brc.mcv.marshaller.DataElementMarshaller
import uk.co.brc.mcv.marshaller.DataTypeMarshaller
import uk.co.brc.mcv.marshaller.ModelMarshaller
import uk.co.brc.mcv.marshaller.ValueDomainMarshaller
import uk.co.mcv.model.DataType
import uk.co.mcv.model.ValueDomain

// Place your Spring DSL code here
beans = {
    customObjectMarshallers( CustomObjectMarshallers ) {
        marshallers = [
                new ModelMarshaller(),
				new DataElementMarshaller(),
				new DataTypeMarshaller(),
				new ValueDomainMarshaller()
        ]
    }
}