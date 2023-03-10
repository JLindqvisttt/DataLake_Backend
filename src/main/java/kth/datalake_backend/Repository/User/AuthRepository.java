package kth.datalake_backend.Repository.User;

import kth.datalake_backend.Entity.User.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface to represent a Neo4j repository for finding {@link User} that represent authentication credentials.
 */
@Repository
public interface AuthRepository extends Neo4jRepository<User, Long> {

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to find
     * @return the User object with the specified username, or null if no such user exists
     */
    User findByUsername(String username);
}
