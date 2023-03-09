package kth.datalake_backend.Entity.Nodes;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import javax.validation.constraints.NotBlank;

/**
 * Symptoms Node
 */
@Node
public class Symptoms {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String symptom;

    @NotBlank
    private int severity;

    /**
     * Class constructor
     */
    public Symptoms() {
    }

    /**
     * Get symptoms
     * @return
     */
    public String getSymptom() {
        return symptom;
    }

    /**
     * Set symptoms
     * @param symptom symptoms to set
     */
    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    /**
     * Get severity of symptom
     * @return
     */
    public int getSeverity() {
        return severity;
    }

    /**
     * Set severity of sympyom
     * @param severity
     */
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
