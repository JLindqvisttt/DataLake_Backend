package kth.datalake_backend.Payload.Request;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCheckPassword() {
        return checkPassword;
    }

    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
    }

    @Override
    public String toString() {
        return "UpdateUserPassword{" +
                "Id=" + Id +
                ", password='" + password + '\'' +
                ", checkPassword='" + checkPassword + '\'' +
                '}';
    }

    public String check(){
        if(password.length() < 6 || password.length() > 20) return "Invalid password must be at least 6 and max 20 characters";
        return null;
    }
}
