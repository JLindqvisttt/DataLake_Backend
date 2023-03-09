package kth.datalake_backend.Repository.Nodes;

import kth.datalake_backend.Entity.Nodes.Symptoms;
import kth.datalake_backend.Entity.Nodes.Treatment;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
public interface SymptomsRepository extends Neo4jRepository<Symptoms, Long> {
    ArrayList<Symptoms> findAll();
}
