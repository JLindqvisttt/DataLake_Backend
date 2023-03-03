package kth.datalake_backend.Service.Util;

import com.fasterxml.jackson.core.JsonGenerator;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import kth.datalake_backend.Entity.Nodes.*;

import java.io.IOException;


public class POJOPatient extends JsonSerializer<Patient> {
    @Override
    public void serialize(Patient patient, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", String.valueOf(patient.getSubjectId()));
        jsonGenerator.writeStringField("age", String.valueOf(patient.getAge()));
        jsonGenerator.writeStringField("gender", String.valueOf(patient.getGender()));
        jsonGenerator.writeStringField("ethnicity", patient.getEthnicity());
        jsonGenerator.writeStringField("relapse", patient.getRelapse());
        jsonGenerator.writeStringField("relapseTime", String.valueOf(patient.getRelapseTime()));
        jsonGenerator.writeStringField("failureFreeSurvivalStatus", String.valueOf(patient.getFailureFreeSurvivalStatus()));
        jsonGenerator.writeStringField("failureFreeSurvivalTime", String.valueOf(patient.getFailureFreeSurvivalTime()));


        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
