package kth.datalake_backend.Payload.Request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Requestbody for signing up a new user
 */
public class SignUpRequest {

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

    /**
     * Get username from Requestbody
     *
     * @return the username from Requestbody
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username for Requestbody
     *
     * @param username the username to use for Requestbody, min = 6, max = 20
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get password from Requestbody
     *
     * @return the password from Requestbody
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password for Requestbody
     *
     * @param password the password to use for Requestbody, min = 6, max = 20
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get firstname from Requestbody
     *
     * @return users firstname that is used from Requestbody
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Set firstname for Requestbody
     *
     * @param firstname the firstname to use for Requestbody, min = 2, max = 20
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Get lastname from Requestbody
     *
     * @return users lastname that is used from Requestbody
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Set lastname for Requestbody
     *
     * @param lastname the lastname to use for Requestbody, min = 2, max = 20
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Method is used to validate input for Requestbody
     *
     * @return null if valid input else return why the input is invalid
     */
    public String check() {
        if (username.length() < 2 || username.length() > 40)
            return "Invalid username must be at least 6 characters and max 40 characters";
        if (firstname.length() < 2 || firstname.length() > 20)
            return "Invalid first name must be at least 2 characters and max 20 characters";
        if (lastname.length() < 2 || lastname.length() > 20)
            return "Invalid last name must be at least 2 characters and max 20 characters";
        if (password.length() < 6 || password.length() > 20)
            return "Invalid password must be at least 6 and max 20 characters";
        return null;
    }
}
