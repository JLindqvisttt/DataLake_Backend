package kth.datalake_backend.Repository.Nodes;

import kth.datalake_backend.Entity.Nodes.Patient;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface PatientRepository extends Neo4jRepository<Patient, Long> {
    ArrayList<Patient> findAll();
    List<Patient> findAllByDataset(String name);
    @Query("MATCH (p:Patient) RETURN DISTINCT p.dataset")
    List<String> findByDatabase();
}
