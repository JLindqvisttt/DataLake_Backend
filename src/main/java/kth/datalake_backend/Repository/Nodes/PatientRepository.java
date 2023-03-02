package kth.datalake_backend.Repository.Nodes;

import kth.datalake_backend.Entity.Nodes.Patient;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;


import java.util.ArrayList;
import java.util.List;

public interface PatientRepository extends Neo4jRepository<Patient, Long> {
    ArrayList<Patient> findAll();
    //@Query("MATCH (p:Patient)-[:BELONGS_TO]->(d:Dataset {name: $name}) RETURN p")
    @Query("MATCH (:Dataset{datasetName: $name})--(p:Patient) RETURN p")
    List<Patient> findAllByDataset(String name);
    //List<Patient> findAllByDataset(@Param("name") String name);
}
