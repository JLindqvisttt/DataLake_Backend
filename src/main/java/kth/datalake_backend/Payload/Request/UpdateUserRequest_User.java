package kth.datalake_backend.Payload.Request;


import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public class UpdateUserRequest_User {

  @Id
  @GeneratedValue
  private Long Id;

  @NotBlank
  @Size(min = 6, max = 20)
  private String password;


  @NotBlank
  @Size(min = 2 ,max = 20)
  private String firstname;

  @NotBlank
  @Size(min = 2 ,max = 20)
  private String lastname;

  @NotBlank
  @Size(min = 6, max = 20)
  private String checkPassword;
  public Long getId() {
    return Id;
  }

  public void setId(Long id) {
    this.Id = id;
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
      "identity=" + Id +
      ", password='" + password + '\'' +
      ", checkPassword='" + checkPassword + '\'' +
      ", firstname='" + firstname + '\'' +
      ", lastname='" + lastname + '\'' +
      '}';
  }

  public String checkName() {

    return null;
  }

  public String checkPassword(){

    return null;
  }
}
