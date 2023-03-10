package kth.datalake_backend.Entity.Nodes;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import javax.validation.constraints.NotBlank;

/**
 * New Malignancy Node
 */
@Node
public class NewMalignancy {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String newMalignancy;

    /**
     * Class constructor
     */
    public NewMalignancy() {
    }

    /**
     * Class constructor, specifies the new malignancy
     *
     * @param newMalignancy the new malignancy to initialize with
     */
    public NewMalignancy(String newMalignancy) {
        this.newMalignancy = newMalignancy;
    }

    /**
     * Get current string of new malignancy
     *
     * @return current string of new malignancy
     */
    public String getNewMalignancy() {
        return newMalignancy;
    }

    /**
     * Set new malignancy depending on the input string
     *
     * @param newMalignancy 1: No,
     *                      2: Yes,
     *                      Unknown if not one of these
     */
    public void setNewMalignancy(String newMalignancy) {
        switch (newMalignancy) {
            case "1.0", "1" -> this.newMalignancy = "No";
            case "2.0", "2" -> this.newMalignancy = "Yes";
            default -> this.newMalignancy = "Unknown";
        }
    }
}
