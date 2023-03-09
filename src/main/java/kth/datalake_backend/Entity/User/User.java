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

/**
 * User Node
 */
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
  private List<String> availableDatasets;

  @NotBlank
  private ERole role;

  /**
   * Class constructor specifying id, firstname, lastname and username
   * @param identity identity
   * @param firstname users firstname
   * @param lastname users lastname
   * @param username users username
   */
  public User(Long identity, String firstname, String lastname,String username) {
    this.Id = identity;
    this.firstName = firstname;
    this.lastName = lastname;
    this.username = username;
  }

  /**
   * Class constructor specifying id, password and username
   * @param identity identity
   * @param password users password
   * @param username users username
   */
  public User(Long identity, String password, String username) {
    this.username = username;
    this.Id = identity;
    this.password = password;
  }

  /**
   * Get available datasets for user
   * @return list of datasets
   */
  public List<String> getAvailableDatasets() {
    return availableDatasets;
  }

  /**
   * Set available dataset if the dataset doesnt already exist in users list
   * @param newdatabase dataset to add
   */
  public void setAvailableDatabases(String newdatabase) {
    if (!availableDatasets.contains(newdatabase)) availableDatasets.add(newdatabase);
  }

  /**
   * Class constructor
   */
  public User() {
  }

  /**
   * Class constructor specifying id, firstname, lastname, username, available datasets and role
   * @param identity identity
   * @param firstName users firstname
   * @param lastName users lastname
   * @param username users username
   * @param availableDatasets list of users available datasets
   * @param role users role
   */
  public User(Long identity, String firstName, String lastName, String username, List<String> availableDatasets, ERole role) {
    this.Id = identity;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.availableDatasets = availableDatasets;
    this.role = role;
  }

  /**
   * Class constructor specifying id, firstname, lastname, username, password, available datasets and role
   * @param identity identity
   * @param firstName users firstname
   * @param lastName users lastname
   * @param username users username
   * @param password users password
   * @param availableDatasets list of users available datasets
   * @param role users role
   */
  public User(Long identity, String firstName, String lastName, String username, String password, List<String> availableDatasets, ERole role) {
    this.Id = identity;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.availableDatasets = availableDatasets;
    this.role = role;
  }

  /**
   * Class constructor specifying id, firstname, lastname, username, password, available datasets and role
   * @param firstName users firstname
   * @param lastName users lastname
   * @param username users username
   * @param password users password
   * @param userRole users role
   */
  public User(String firstName, String lastName, String username, String password, ERole userRole) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.role = userRole;
    this.availableDatasets = new ArrayList<>();
  }

  /**
   * Class constructor specifying id, firstname, lastname, username, password, available datasets and role
   * @param firstName users firstname
   * @param lastName users lastname
   * @param username users username
   * @param password users password
   */
  public User(String firstName, String lastName, String username, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.availableDatasets = new ArrayList<>();
  }

  /**
   * Set available dataset
   * @param availableDatasets list of available datasets
   */
  public void setAvailableDatabases(List<String> availableDatasets) {
    this.availableDatasets = availableDatasets;
  }

  /**
   * get users role
   * @return enum of users role
   * @see ERole
   */
  public ERole getRole() {
    return role;
  }

  /**
   * Set users role
   * @param role enum of role
   */
  public void setRole(ERole role) {
    this.role = role;
  }

  /**
   * Get list of datasets
   * @return
   */
  public List<String> getDatabases() {
    return availableDatasets;
  }

  /**
   * Set datasets, if list doesn't already contain this dataset
   * @param newDatabase dataset to add
   */
  public void setDatabases(String newDatabase) {
    if (!availableDatasets.contains(newDatabase)) this.availableDatasets.add(newDatabase);
  }

  /**
   * Get users firstname
   * @return users firstname
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Set users firstname
   * @param firstName name to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Get users lastname
   * @return users lastname
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Get users id
   * @return users id
   */
  public Long getId() {
    return Id;
  }

  /**
   * Set users id
   * @param id the id to set
   */
  public void setId(Long id) {
    this.Id = id;
  }

  /**
   * Set users lastname
   * @param lastName lastname to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Get users username
   * @return username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Set username
   * @param username username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Get users password
   * @return password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Set users password
   * @param password password to set
   */
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
      ", availableDatabases=" + availableDatasets +
      ", roles=" + role +
      '}';
  }
}
