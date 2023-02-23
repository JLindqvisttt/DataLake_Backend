package kth.datalake_backend.Repository.Nodes;

import kth.datalake_backend.Entity.Nodes.CauseOfDeath;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.ArrayList;

public interface CauseOfDeathRepository extends Neo4jRepository<CauseOfDeath, Long> {
    ArrayList<CauseOfDeath> findAll();
}