package kth.datalake_backend.Entity.Nodes;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import javax.validation.constraints.NotBlank;

/**
 * Cause of Death Node
 */
@Node
public class CauseOfDeath {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String causeOfDeath;

    /**
     * Class constructor
     */
    public CauseOfDeath(){}

    /**
     * Get current cause of death
     * @return
     */
    public String getCauseOfDeath() {
        return causeOfDeath;
    }

    /**
     * Set cause of death depending on the input string
     * @param causeOfDeath the cause of death, string value of 1 - 3, unknown if not one of these values
     */
    public void setCauseOfDeath(String causeOfDeath) {
        switch (causeOfDeath) {
            case "1.0","1" -> this.causeOfDeath = "Protocol treatment related";
            case "2.0","2" -> this.causeOfDeath = "Protocol disease related";
            case "3.0","3" -> this.causeOfDeath = "Not related to protocol treatment or protocol disease";
            default -> this.causeOfDeath = "Unknown";
        }
    }
}
