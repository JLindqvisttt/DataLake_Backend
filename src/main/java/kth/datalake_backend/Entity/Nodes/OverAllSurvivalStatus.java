package kth.datalake_backend.Entity.Nodes;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import javax.validation.constraints.NotBlank;

@Node
public class OverAllSurvivalStatus {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String overAllSurvivalStatus;

    public OverAllSurvivalStatus() {}

    public String getOverAllSurvivalStatus() {
        return overAllSurvivalStatus;
    }

    public void setOverAllSurvivalStatus(String overAllSurvivalStatus) {
        switch (overAllSurvivalStatus) {
            case "1.0": this.overAllSurvivalStatus = "Alive";
                break;
            case "2.0": this.overAllSurvivalStatus ="Death";
                break;
            case "3.0": this.overAllSurvivalStatus ="Lost to follow-up";
                break;
            default: this.overAllSurvivalStatus = "Unknown";
        }
    }
}
