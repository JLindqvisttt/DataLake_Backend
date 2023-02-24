package kth.datalake_backend.Entity.Nodes;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import javax.validation.constraints.NotBlank;

@Node
public class NewMalignancy {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String newMalignancy;

    public NewMalignancy(){}

    public NewMalignancy(String newMalignancy){
        this.newMalignancy = newMalignancy;
    }

    public String getNewMalignancy() {
        return newMalignancy;
    }

    public void setNewMalignancy(String newMalignancy) {
        switch (newMalignancy) {
            case "1.0" -> this.newMalignancy = "No";
            case "2.0" -> this.newMalignancy = "Yes";
            default -> this.newMalignancy = "Unknown";
        }
    }
}
