package kth.datalake_backend.Entity.User;

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
  private Long Id;

  @NotBlank
  @Size(min = 2 ,max = 20)
  private String firstName;
  @NotBlank
  @Size(min = 2,max = 20)
  private String lastName;

  @NotBlank
  @Size(min = 6,max = 40)
  @Email
  private String username;

  @JsonIgnore
  @NotBlank
  @Size(min = 6,max = 20)
  private String password;

  @NotBlank
  private List<String> availableDatabases;

  @NotBlank
  private ERole role;

  public User(Long identity, String firstname, String lastname,String username) {
    this.Id = identity;
    this.firstName = firstname;
    this.lastName = lastname;
    this.username = username;
  }
  public User(Long identity, String password, String username) {
    this.username = username;
    this.Id = identity;
    this.password = password;
  }
  public List<String> getAvailableDatabases() {
    return availableDatabases;
  }

  public void setAvailableDatabases(String newdatabase) {
    if (!availableDatabases.contains(newdatabase)) availableDatabases.add(newdatabase);
  }


  public User() {

  }
  public User(Long identity, String firstName, String lastName, String username, List<String> availableDatabases, ERole role) {
    this.Id = identity;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.availableDatabases = availableDatabases;
    this.role = role;
  }
  public User(Long identity, String firstName, String lastName, String username, String password, List<String> availableDatabases, ERole role) {
    this.Id = identity;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.availableDatabases = availableDatabases;
    this.role = role;
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

  public Long getId() {
    return Id;
  }

  public void setId(Long id) {
    this.Id = id;
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
      "identity=" + Id +
      ", firstName='" + firstName + '\'' +
      ", lastName='" + lastName + '\'' +
      ", username='" + username + '\'' +
      ", password='" + password + '\'' +
      ", availableDatabases=" + availableDatabases +
      ", roles=" + role +
      '}';
  }
}
