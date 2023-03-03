package kth.datalake_backend.Service.Util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import kth.datalake_backend.Entity.Nodes.Treatment;

import java.io.IOException;

public class POJOTreatment extends JsonSerializer<Treatment> {
    @Override
    public void serialize(Treatment treatment, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("treatment", String.valueOf(treatment.getTreatment()));
        jsonGenerator.writeEndObject();
    }
}
