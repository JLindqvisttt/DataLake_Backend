package kth.datalake_backend.Repository.Nodes;

import kth.datalake_backend.Entity.Nodes.Patient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface PatientRepository extends Neo4jRepository<Patient, Long> {
    ArrayList<Patient> findAll();

    @Query("MATCH (:Dataset{datasetName: $name})--(p:Patient) RETURN p")
    List<Patient> findAllByDataset(String name);

    @Query()
    ArrayList<Patient> findPatientBySubjectId(int subId);

    @Query("MATCH (:Dataset{datasetName: $name})--(actor:Patient)-[r]->(b)WITH actor, collect(b) AS nodes RETURN actor{actor, b: nodes}")
    List<Patient> findPatientByDataset(String name);
}
