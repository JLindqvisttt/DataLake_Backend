package kth.datalake_backend.Repository.Nodes;

import kth.datalake_backend.Entity.Nodes.Symptoms;
import kth.datalake_backend.Entity.Nodes.Treatment;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.ArrayList;

public interface SymptomsRepository extends Neo4jRepository<Symptoms, Long> {
    ArrayList<Symptoms> findAll();
}