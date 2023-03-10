package kth.datalake_backend.Repository.Nodes;

import kth.datalake_backend.Entity.Nodes.Treatment;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Interface to represent a repository for Treatment objects, which are persisted in a Neo4j database.
 */
@Repository
public interface TreatmentRepository extends Neo4jRepository<Treatment, Long> {

    /**
     * Retrieves all Treatment nodes in the database.
     *
     * @return list of all Treatment nodes.
     */
    ArrayList<Treatment> findAll();
}
