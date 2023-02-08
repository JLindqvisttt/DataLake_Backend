package kth.datalake_backend.Payload.Response;

import kth.datalake_backend.Entity.ERole;

import java.util.List;

public class JwtResponse {

  private String token;
  private String type = "Bearer";
  private Long id;
  private String email;
  private String firstname;
  private String lastname;

  private String role;
  private List<String> availableDatabases;

  public JwtResponse(String token, Long id, String email, String firstname, String lastname, String role, List<String> availableDatabases) {
    this.token = token;
    this.id = id;
    this.email = email;
    this.firstname = firstname;
    this.lastname = lastname;
    this.role = role;
    this.availableDatabases = availableDatabases;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public List<String> getAvailableDatabases() {
    return availableDatabases;
  }

  public void setAvailableDatabases(List<String> availableDatabases) {
    this.availableDatabases = availableDatabases;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
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
    return "JwtResponse{" +
      "token='" + token + '\'' +
      ", type='" + type + '\'' +
      ", id=" + id +
      ", email='" + email + '\'' +
      ", firstname='" + firstname + '\'' +
      ", lastname='" + lastname + '\'' +
      ", role=" + role +
      ", availableDatabases=" + availableDatabases +
      '}';
  }
}
