package kth.datalake_backend.Entity;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Node
public class User {

  @Id @GeneratedValue private Long id;

  @NotBlank
  @Size(max = 50)
  private String firstName;
  @NotBlank
  @Size(max = 50)
  private String lastName;

  @NotBlank
  @Size(max = 50)
  @Email
  private String username;

  @JsonIgnore
  @NotBlank
  @Size(max = 120)
  private String password;

  public User(){

  }

  public User(String firstName, String lastName, String username, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

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

  @Override
  public String toString() {
    return
      "id=" + id +
      ", firstName='" + firstName + '\'' +
      ", lastName='" + lastName + '\'' +
      ", username='" + username + '\'' +
      ", password='" + password + "\n" ;
  }
}
