package kth.datalake_backend.Entity.Nodes;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import javax.validation.constraints.NotBlank;

@Node
public class Symptoms {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String symptom;

    @NotBlank
    private int severity;

    public Symptoms() {
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    @Override
    public String toString() {
        return "Symptoms{" +
                "id=" + id +
                ", symptom='" + symptom + '\'' +
                ", severity=" + severity +
                '}';
    }
}
