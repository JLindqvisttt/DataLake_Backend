package kth.datalake_backend.Entity.Nodes;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import javax.validation.constraints.NotBlank;

@Node
public class Dataset {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Property("datasetName")
    private String datasetName;


    public Dataset() {
    }

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    @Override
    public String toString() {
        return "datasetName: " + datasetName;
    }
}
