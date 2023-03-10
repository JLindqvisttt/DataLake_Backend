package kth.datalake_backend.Payload.Request;

import kth.datalake_backend.Entity.User.ERole;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Requestbody for updating a user for an admin
 */
public class UpdateUserRequest_Admin {
    @Id
    @GeneratedValue
    private Long Id;

    @NotBlank
    @Size(min = 6, max = 40)
    @Email
    private String username;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    @NotBlank
    @Size(min = 2, max = 20)
    private String firstname;

    @NotBlank
    @Size(min = 2, max = 20)
    private String lastname;

    @NotBlank
    private List<String> availableDatasets;

    @NotBlank
    private ERole role;

    /**
     * Get username for the update request
     *
     * @return the username that is used for updating a user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set username for the update request
     *
     * @param username the username for the user that will be updated, min = 6, max = 40
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get password for the update request
     *
     * @return the password that is used for updating a user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password for the update request
     *
     * @param password the password for the user that will be updated, min = 6, max 20
     */
    public void setPassword(String password) {
        this.password = password;
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
     * Get available dataset for user
     * @return a list of dataset
     */
    public List<String> getAvailableDatasets() {
        return availableDatasets;
    }

    /**
     * Set available datasets for user
     * @param availableDatasets a list of available datasets
     */
    public void setAvailableDatasets(List<String> availableDatasets) {
        this.availableDatasets = availableDatasets;
    }

    /**
     * Get user's role
     * @return the user's role, enum
     */
    public ERole getRole() {
        return role;
    }

    /**
     * Set user's role
     * @param role role to be set for user
     */
    public void setRole(ERole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UpdateUserRequest{" +
                "identity=" + Id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", availableDatabases=" + availableDatasets +
                ", role=" + role +
                '}';
    }
}
