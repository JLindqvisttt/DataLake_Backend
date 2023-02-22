package kth.datalake_backend.Repository;

import kth.datalake_backend.Entity.Patient;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PatientRepository extends Neo4jRepository<Patient, Long> {

}
