package kth.datalake_backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Node
public class User {

  @Id
  @GeneratedValue
  private Long identity;

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

  @NotBlank
  private List<String> availableDatabases;

  @NotBlank
  private ERole role;

  public List<String> getAvailableDatabases() {
    return availableDatabases;
  }

  public void setAvailableDatabases(String newdatabase) {
    if (!availableDatabases.contains(newdatabase)) availableDatabases.add(newdatabase);
  }


  public User() {

  }

  public User(String firstName, String lastName, String username, String password, ERole userRole) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.role = userRole;
    this.availableDatabases = new ArrayList<>();
  }

  public User(String firstName, String lastName, String username, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.availableDatabases = new ArrayList<>();
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

  public List<String> getDatabases() {
    return availableDatabases;
  }

  public void setDatabases(String newDatabase) {
    if (!availableDatabases.contains(newDatabase)) this.availableDatabases.add(newDatabase);
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

  public Long getIdentity() {
    return identity;
  }

  public void setIdentity(Long identity) {
    this.identity = identity;
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
    return "User{" +
      "identity=" + identity +
      ", firstName='" + firstName + '\'' +
      ", lastName='" + lastName + '\'' +
      ", username='" + username + '\'' +
      ", password='" + password + '\'' +
      ", availableDatabases=" + availableDatabases +
      ", roles=" + role +
      '}';
  }
}
