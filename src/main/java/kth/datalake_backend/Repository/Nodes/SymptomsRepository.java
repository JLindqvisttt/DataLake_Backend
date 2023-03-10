package kth.datalake_backend.Repository.Nodes;

import kth.datalake_backend.Entity.Nodes.Symptoms;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Interface to represent a repository for Symptomps objects, which are persisted in a Neo4j database.
 */
@Repository
public interface SymptomsRepository extends Neo4jRepository<Symptoms, Long> {

    /**
     * Retrieves all Symptoms nodes in the database.
     *
     * @return list of all Symptoms nodes.
     */
    ArrayList<Symptoms> findAll();
}
