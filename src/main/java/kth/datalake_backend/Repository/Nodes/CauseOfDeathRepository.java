package kth.datalake_backend.Repository.Nodes;

import kth.datalake_backend.Entity.Nodes.CauseOfDeath;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Interface to represent a repository for CauseOfDeath objects, which are persisted in a Neo4j database
 */
@Repository
public interface CauseOfDeathRepository extends Neo4jRepository<CauseOfDeath, Long> {

    /**
     * Retrieves a list of all CauseOfDeath objects in the database.
     *
     * @return An ArrayList containing all CauseOfDeath objects in the database.
     */
    ArrayList<CauseOfDeath> findAll();
}
