package kth.datalake_backend.Entity.Nodes;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import javax.validation.constraints.NotBlank;

@Node
public class CauseOfDeath {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String causeOfDeath;

    public CauseOfDeath(){}

    public String getCauseOfDeath() {
        return causeOfDeath;
    }

    public void setCauseOfDeath(String causeOfDeath) {
        switch (causeOfDeath) {
            case "1.0" -> this.causeOfDeath = "Protocol treatment related";
            case "2.0" -> this.causeOfDeath = "Protocol disease related";
            case "3.0" -> this.causeOfDeath = "Not related to protocol treatment or protocol disease";
            default -> this.causeOfDeath = "Unknown";
        }
    }
}
