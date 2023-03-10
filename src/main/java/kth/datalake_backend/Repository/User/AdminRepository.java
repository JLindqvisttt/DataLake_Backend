package kth.datalake_backend.Repository.User;

import kth.datalake_backend.Entity.User.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface to represent a Neo4j repository for managing {@link User} objects with an admin role.
 */

@Repository
public interface AdminRepository extends Neo4jRepository<User, Long> {

    /**
     * Retrieves all users with an admin role.
     *
     * @return a list of {@link User} objects
     */
    List<User> findAll();

    /**
     * Checks if a user with the specified username exists.
     *
     * @param username the username to search for
     * @return true if a user with the specified username exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Retrieves the number of nodes in the graph database.
     *
     * @return a list containing the number of nodes
     */
    @Query("MATCH (n) RETURN count(n) AS numberOfNodes")
    List<Long> nrOfNodes();

    /**
     * Retrieves the number of relationships in the graph database.
     *
     * @return a list containing the number of relationships
     */
    @Query("MATCH ()-[r]->() RETURN count(r) AS numberOfRelationships")
    List<Long> nrOfRelations();

    /**
     * Adds a database to the available databases of all admin users who don't already have it.
     *
     * @param databaseName the name of the database to add
     */
    @Query("MATCH (u:User {role: 'ROLE_ADMIN'}) WHERE NOT $databaseName IN u.availableDatabases SET u.availableDatabases = u.availableDatabases + $databaseName")
    void addDatabaseToAdminUsers(String databaseName);
}
