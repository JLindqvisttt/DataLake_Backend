package kth.datalake_backend.Repository;

import kth.datalake_backend.Entity.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface AuthRepository extends Neo4jRepository<User, Long> {

  boolean existsByUsername(String username);
  User findByUsername(String username);
}
