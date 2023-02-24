package kth.datalake_backend.Repository.Nodes;

import kth.datalake_backend.Entity.Nodes.Patient;
import org.springframework.data.neo4j.repository.Neo4jRepository;


import java.util.ArrayList;

public interface PatientRepository extends Neo4jRepository<Patient, Long> {
    ArrayList<Patient> findAll();

}
