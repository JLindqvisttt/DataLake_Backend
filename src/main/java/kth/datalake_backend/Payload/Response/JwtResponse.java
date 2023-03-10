package kth.datalake_backend.Payload.Response;

import java.util.List;

/**
 * ResponseBody for the JWT when a user is signing in
 */
public class JwtResponse {


    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private String firstname;
    private String lastname;
    private String role;
    private List<String> availableDatasets;

    /**
     * Class constructor for JwtResponse
     *
     * @param token              The token generated by the authentication server
     * @param id                 The ID of the user
     * @param email              The email of the user
     * @param firstname          The first name of the user
     * @param lastname           The last name of the user
     * @param role               The role of the user
     * @param availableDatabases The list of available databases for the user
     */
    public JwtResponse(String token, Long id, String email, String firstname, String lastname, String role, List<String> availableDatabases) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
        this.availableDatasets = availableDatabases;
    }

    /**
     * Getter method for the role property
     *
     * @return The role of the user
     */
    public String getRole() {
        return role;
    }

    /**
     * Set role for user
     *
     * @param role The role of the user
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Get available available datasets for user
     *
     * @return list of available datasets for the user
     */
    public List<String> getAvailableDatasets() {
        return availableDatasets;
    }

    /**
     * Set available datasets for user
     *
     * @param availableDatasets list of available databases for the user
     */
    public void setAvailableDatasets(List<String> availableDatasets) {
        this.availableDatasets = availableDatasets;
    }

    /**
     * Get token for user
     *
     * @return the token generated by the authentication server
     */
    public String getToken() {
        return token;
    }

    /**
     * Set token for user
     *
     * @param token the token generated by the authentication server
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Get token type
     *
     * @return the type of token
     */
    public String getType() {
        return type;
    }

    /**
     * Set type for token
     *
     * @param type the type of token
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the users id from JwtResponse
     * @return user id from JwtResponse
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id of the user for database identification
     * @param id set id of the user
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the email of the user associated with this JwtResponse
     *
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user associated with this JwtResponse
     *
     * @param email The new email to set for the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get firstname of the user associated with this JwtResponse
     *
     * @return The first name of the user.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Set the firstname of the user associated with this JwtResponse
     *
     * @param firstname firstname to set for the user
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Get lastname of the user associated with this JwtResponse
     *
     * @return the lastname of the user
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Set the lastname of the user associated with this JwtResponse
     *
     * @param lastname lastname to set for the user
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "JwtResponse{" +
                "token='" + token + '\'' +
                ", type='" + type + '\'' +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", role=" + role +
                ", availableDatabases=" + availableDatasets +
                '}';
    }
}
