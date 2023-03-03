package kth.datalake_backend.Repository.Nodes;

import kth.datalake_backend.Entity.Nodes.Patient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface PatientRepository extends Neo4jRepository<Patient, Long> {
    ArrayList<Patient> findAll();

    @Query("MATCH (:Dataset{datasetName: $name})--(p:Patient) RETURN p")
    List<Patient> findAllByDataset(String name);

    @Query("CALL apoc.export.json.query('MATCH(:Dataset{datasetName: $name})--(a:Patient)-[r]-(b) RETURN a,b', null, {}) YIELD file RETURN file")
    String findPatientByDataset(@Param("name") String name);
    
}
