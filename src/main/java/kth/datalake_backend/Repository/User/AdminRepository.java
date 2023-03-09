package kth.datalake_backend.Repository.User;

import kth.datalake_backend.Entity.User.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends Neo4jRepository<User, Long> {
  List<User> findAll();

  boolean existsByUsername(String username);

  @Query("MATCH (n) RETURN count(n) AS numberOfNodes")
  List<Long> nrOfNodes();

  @Query("MATCH ()-[r]->() RETURN count(r) AS numberOfRelationships")
  List<Long> nrOfRelations();

  @Query("MATCH (u:User {role: 'ROLE_ADMIN'}) WHERE NOT $databaseName IN u.availableDatabases SET u.availableDatabases = u.availableDatabases + $databaseName")
  void addDatabaseToAdminUsers(String databaseName);
}
