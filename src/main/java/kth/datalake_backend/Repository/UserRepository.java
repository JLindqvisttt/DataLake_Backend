package kth.datalake_backend.Repository;

import kth.datalake_backend.Entity.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface UserRepository extends Neo4jRepository<User, Long> {
  User findByEmail(String name);
  List<User> findAll();
}
