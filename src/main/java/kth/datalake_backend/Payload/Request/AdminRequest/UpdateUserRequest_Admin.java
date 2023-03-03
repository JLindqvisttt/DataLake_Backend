package kth.datalake_backend.Payload.Request.AdminRequest;

import kth.datalake_backend.Entity.User.ERole;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public class UpdateUserRequest_Admin {
    @Id
    @GeneratedValue
    private Long identity;

    @NotBlank
    @Size(max = 50)
    @Email
    private String username;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    @Size(max = 50)
    private String firstname;

    @NotBlank
    @Size(max = 50)
    private String lastname;

    @NotBlank
    private List<String> availableDatabases;

    @NotBlank
    private ERole role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Long getIdentity() {
        return identity;
    }

    public void setIdentity(Long identity) {
        this.identity = identity;
    }

    public List<String> getAvailableDatabases() {
        return availableDatabases;
    }

    public void setAvailableDatabases(List<String> availableDatabases) {
        this.availableDatabases = availableDatabases;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UpdateUserRequest{" +
                "identity=" + identity +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", availableDatabases=" + availableDatabases +
                ", role=" + role +
                '}';
    }
}
