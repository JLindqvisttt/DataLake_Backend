package kth.datalake_backend.Payload.Request;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public class UpdateUserRequest_User {

  @Id
  @GeneratedValue
  private Long identity;

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
  @Size(min = 6, max = 40)
  private String checkPassword;
  public Long getIdentity() {
    return identity;
  }

  public void setIdentity(Long identity) {
    this.identity = identity;
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
    return "UpdateUserRequest_User{" +
      "identity=" + identity +
      ", password='" + password + '\'' +
      ", checkPassword='" + checkPassword + '\'' +
      ", firstname='" + firstname + '\'' +
      ", lastname='" + lastname + '\'' +
      '}';
  }
}
