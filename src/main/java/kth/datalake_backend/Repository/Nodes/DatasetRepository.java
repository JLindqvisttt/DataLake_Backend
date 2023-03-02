package kth.datalake_backend.Repository.Nodes;

import kth.datalake_backend.Entity.Nodes.Dataset;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.ArrayList;

public interface DatasetRepository extends Neo4jRepository<Dataset, Long> {
    ArrayList<Dataset> findAll();
}
