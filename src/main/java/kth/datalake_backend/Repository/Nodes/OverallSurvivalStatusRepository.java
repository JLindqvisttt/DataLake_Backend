package kth.datalake_backend.Repository.Nodes;

import kth.datalake_backend.Entity.Nodes.OverAllSurvivalStatus;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.ArrayList;

public interface OverallSurvivalStatusRepository extends Neo4jRepository<OverAllSurvivalStatus, Long> {
    ArrayList<OverAllSurvivalStatus> findAll();
}