package kth.datalake_backend.Repository.Nodes;

import kth.datalake_backend.Entity.Nodes.OverAllSurvivalStatus;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Interface to represent a repository for OverallSurvivalStatus objects, which are persisted in a Neo4j database.
 */
@Repository
public interface OverallSurvivalStatusRepository extends Neo4jRepository<OverAllSurvivalStatus, Long> {

    /**
     * Retrieves a list of all OverallSurvivalStatus objects in the database.
     *
     * @return An ArrayList containing all OverallSurvivalStatus objects in the database.
     */
    ArrayList<OverAllSurvivalStatus> findAll();
}
