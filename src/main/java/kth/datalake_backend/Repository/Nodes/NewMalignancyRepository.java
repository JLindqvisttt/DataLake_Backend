package kth.datalake_backend.Repository.Nodes;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import kth.datalake_backend.Entity.Nodes.NewMalignancy;
import java.util.ArrayList;

public interface NewMalignancyRepository extends Neo4jRepository<NewMalignancy, Long>{
    ArrayList<NewMalignancy> findAll();
}
