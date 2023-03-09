package kth.datalake_backend.Entity.Nodes;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import javax.validation.constraints.NotBlank;

/**
 * Overall survival status Node
 */
@Node
public class OverAllSurvivalStatus {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String overAllSurvivalStatus;

    /**
     * Class constructor
     */
    public OverAllSurvivalStatus() {}

    /**
     * Get current overall survival status
     * @return
     */
    public String getOverAllSurvivalStatus() {
        return overAllSurvivalStatus;
    }

    /**
     * Set overall survival status depending on the input string and dataset name
     * @param overAllSurvivalStatus string value 1-3, unknown if not one of these
     * @param name name of the dataset
     */
    public void setOverAllSurvivalStatus(String overAllSurvivalStatus, String name) {
        if(name.equals("266"))overAllSurvivalStatus = String.valueOf(Double.valueOf(overAllSurvivalStatus)+1);
        switch (overAllSurvivalStatus) {
            case "1.0","1" -> this.overAllSurvivalStatus = "Alive";
            case "2.0","2" -> this.overAllSurvivalStatus = "Death";
            case "3.0","3" -> this.overAllSurvivalStatus = "Lost to follow-up";
            default -> this.overAllSurvivalStatus = "Unknown";
        }
    }
}
