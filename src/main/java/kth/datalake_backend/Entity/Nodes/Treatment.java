package kth.datalake_backend.Entity.Nodes;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import javax.validation.constraints.NotBlank;

/**
 * Treatment Node
 */
@Node
public class Treatment {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String treatment;

    /**
     * Class constructor, specify the treatment
     * @param treatment name of treatment
     */
    public Treatment(String treatment){
        this.treatment = treatment;
    }

    /**
     * Get treatment
     * @return treatment
     */
    public String getTreatment(){
        return treatment;
    }

    /**
     * Set treatment
     * @param treatment name of treatment
     */
    public void setTreatment(String treatment){
        this.treatment = treatment;
    }
}
