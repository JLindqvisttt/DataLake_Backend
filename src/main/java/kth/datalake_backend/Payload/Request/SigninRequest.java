package kth.datalake_backend.Payload.Request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Requestbody for sign in a user
 */
public class SigninRequest {

    @NotBlank
    @Size(min = 2, max = 20)
    private String username;

    @NotBlank
    @Size(min = 2, max = 20)
    private String password;

    /**
     * Get username from Requestbody
     *
     * @return the username that is used for sign in
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set username for Requestbody
     *
     * @param username the username to use for sign in request, min = 2, max = 20
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get password from Requestbody
     *
     * @return the password that is used for sign in request
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password for Requestbody
     *
     * @param password the password to use for sign in request, min = 2, max = 20
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
