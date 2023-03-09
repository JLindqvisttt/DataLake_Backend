package kth.datalake_backend.Repository.Nodes;

import kth.datalake_backend.Entity.Nodes.OverAllSurvivalStatus;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
public interface OverallSurvivalStatusRepository extends Neo4jRepository<OverAllSurvivalStatus, Long> {
    ArrayList<OverAllSurvivalStatus> findAll();
}
