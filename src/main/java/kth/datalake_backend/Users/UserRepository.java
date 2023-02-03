package kth.datalake_backend.Users;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface UserRepository extends Neo4jRepository<User, Long> {
  User findByUsername(String username);
  List<User> findAll();
}
