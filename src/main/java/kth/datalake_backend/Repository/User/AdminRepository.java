package kth.datalake_backend.Repository.User;

import kth.datalake_backend.Entity.User.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface AdminRepository extends Neo4jRepository<User, Long> {

  User findByIdentity(Long identity);
  List<User> findAll();
  boolean existsByUsername(String username);

}
