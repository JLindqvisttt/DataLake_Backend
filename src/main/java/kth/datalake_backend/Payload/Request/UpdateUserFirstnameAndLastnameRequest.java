package kth.datalake_backend.Payload.Request;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Requestbody for updating a users first and last name
 */
public class UpdateUserFirstnameAndLastnameRequest {
    @Id
    @GeneratedValue
    private Long Id;

    @NotBlank
    @Size(min = 2 ,max = 20)
    private String firstname;

    @NotBlank
    @Size(min = 2 ,max = 20)
    private String lastname;

    /**
     * Get the users id from Requestbody
     * @return user id from Requestbody
     */
    public Long getId() {
        return Id;
    }

    /**
     * Set id of the user for database identification
     * @param id set id of the user
     */
    public void setId(Long id) {
        Id = id;
    }

    /**
     * Get firstname for the update request
     * @return the firstname that is used for updating a user
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Set firstname for the update request
     * @param firstname the firstname for the user that will be updated, min = 2, max = 20
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Get lastname for the update request
     * @return the lastname that is used for updating a user
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Set lastname for the update request
     * @param lastname the lastname for the user that will be updated, min = 2, max = 20
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "UpdateUserUsername{" +
                "Id=" + Id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }

    /**
     * Check if the new first and last name are valid, min = 2, max = 20
     * @return returns invalid string if invalid, otherwise null
     */
    public String check(){
        if(firstname.length() < 2 || firstname.length() > 20) return "Invalid first name must be at least 2 characters and max 20 characters";
        if(lastname.length() < 2 || lastname.length() > 20) return "Invalid last name must be at least 2 characters and max 20 characters";
        return null;
    }
}
