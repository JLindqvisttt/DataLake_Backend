package kth.datalake_backend.Repository.Nodes;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import kth.datalake_backend.Entity.Nodes.NewMalignancy;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Interface to represent a repository for NewMalignancy objects, which are persisted in a Neo4j database
 */
@Repository
public interface NewMalignancyRepository extends Neo4jRepository<NewMalignancy, Long> {

    /**
     * Retrieves a list of all NewMalignancy objects in the database.
     *
     * @return An ArrayList containing all NewMalignancy objects in the database.
     */
    ArrayList<NewMalignancy> findAll();
}
