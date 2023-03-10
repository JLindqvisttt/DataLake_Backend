package kth.datalake_backend.Payload.Request;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Requestbody for updating a users password
 */
public class UpdateUserPasswordRequest {
    @Id
    @GeneratedValue
    private Long Id;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    @NotBlank
    @Size(min = 6, max = 20)
    private String checkPassword;

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
     * Get the new password of the user
     * @return String of the new password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the new password of the user min = 6, max = 20
     * @param password set the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the original password of the user
     * @return get the original password of the user
     */
    public String getCheckPassword() {
        return checkPassword;
    }

    /**
     * Set the original password string of the user password min = 6, max = 20
     * @param checkPassword set users original
     */
    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
    }

    /**
     * To string
     * @return string of class
     */
    @Override
    public String toString() {
        return "UpdateUserPassword{" +
                "Id=" + Id +
                ", password='" + password + '\'' +
                ", checkPassword='" + checkPassword + '\'' +
                '}';
    }

    /**
     * Check if input is valid
     * @return null if valid, otherwise return why it is invalid
     */
    public String check(){
        if(password.length() < 6 || password.length() > 20) return "Invalid password must be at least 6 and max 20 characters";
        return null;
    }
}
