package kth.datalake_backend.Payload.Request;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

/**
 * Requestbody for removing a user
 */

public class RemoveUserRequest {

    @Id
    @GeneratedValue
    private Long Id;

    /**
     * Get userId from Requestbody
     *
     * @return userId from Requestbody
     */
    public Long getId() {
        return Id;
    }

    /**
     * Set userId for Requestbody
     *
     * @param id ID to set (using Long)
     */
    public void setId(Long id) {
        this.Id = id;
    }

    @Override
    public String toString() {
        return "RemoveUserRequest{" +
                "identity=" + Id +
                '}';
    }
}
