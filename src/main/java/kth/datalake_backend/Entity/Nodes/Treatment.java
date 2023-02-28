package kth.datalake_backend.Entity.Nodes;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import javax.validation.constraints.NotBlank;

@Node
public class Treatment {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String treatment;

    public Treatment(String treatment){
        this.treatment = treatment;
    }

    public String getTreatment(){
        return treatment;
    }

    public void setTreatment(String treatment){
        this.treatment = treatment;
    }

    @Override
    public String toString() {
        return "Treatment:" + treatment ;
    }
}
