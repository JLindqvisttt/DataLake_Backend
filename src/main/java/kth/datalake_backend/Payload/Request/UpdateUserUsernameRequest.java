package kth.datalake_backend.Payload.Request;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UpdateUserUsernameRequest {
    @Id
    @GeneratedValue
    private Long Id;

    @NotBlank
    @Size(min = 2 ,max = 20)
    private String firstname;

    @NotBlank
    @Size(min = 2 ,max = 20)
    private String lastname;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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

    @Override
    public String toString() {
        return "UpdateUserUsername{" +
                "Id=" + Id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }

    public String check(){
        if(firstname.length() < 2 || firstname.length() > 20) return "Invalid first name must be at least 2 characters and max 20 characters";
        if(lastname.length() < 2 || lastname.length() > 20) return "Invalid last name must be at least 2 characters and max 20 characters";
        return null;
    }
}
