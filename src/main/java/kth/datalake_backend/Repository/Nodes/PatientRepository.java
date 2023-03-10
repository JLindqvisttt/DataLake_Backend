package kth.datalake_backend.Repository.Nodes;

import kth.datalake_backend.Entity.Nodes.Patient;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface to represent a repository for Patient objects, which are persisted in a Neo4j database.
 */
@Repository
public interface PatientRepository extends Neo4jRepository<Patient, Long> {

    /**
     * Retrieves a list of all Patient objects in the database.
     *
     * @return An ArrayList containing all Patient objects in the database.
     */
    ArrayList<Patient> findAll();

    /**
     * Retrieves a list of all Patient objects in the database that belong to a specific dataset.
     *
     * @param name The name of the dataset to filter by.
     * @return A List containing all Patient objects that belong to the specified dataset.
     */
    List<Patient> findAllByDataset(String name);

    /**
     * Retrieves a list of all distinct dataset names in the database.
     *
     * @return A List containing all distinct dataset names in the database.
     */
    @Query("MATCH (p:Patient) RETURN DISTINCT p.dataset")
    List<String> findByDatabase();
}
