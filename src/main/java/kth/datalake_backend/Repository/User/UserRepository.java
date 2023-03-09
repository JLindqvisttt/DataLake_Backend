package kth.datalake_backend.Repository.User;

import kth.datalake_backend.Entity.User.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {
  User findByUsername(String username);
}
