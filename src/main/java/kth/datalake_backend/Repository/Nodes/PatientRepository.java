package kth.datalake_backend.Repository.Nodes;

import kth.datalake_backend.Entity.Nodes.Patient;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;


import java.util.ArrayList;
import java.util.List;

public interface PatientRepository extends Neo4jRepository<Patient, Long> {
    ArrayList<Patient> findAll();

    @Query("MATCH (:Dataset{datasetName: $name})--(p:Patient) RETURN p")
    List<Patient> findAllByDataset(String name);

}
