package kth.datalake_backend.Payload.Request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpRequest {

  @NotBlank
  @Size(min = 6,max = 40)
  @Email
  private String username;

  @NotBlank
  @Size(min = 6, max = 20)
  private String password;

  @NotBlank
  @Size(min = 2, max = 20)
  private String firstname;

  @NotBlank
  @Size(min = 2, max = 20)
  private String lastname;

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

  public String check() {
    if(username.length() < 2 || username.length() >40) return "Invalid username must be at least 6 characters and max 40 characters";
    if(firstname.length() < 2 || firstname.length() > 20) return "Invalid first name must be at least 2 characters and max 20 characters";
    if(lastname.length() < 2 || lastname.length() > 20) return "Invalid last name must be at least 2 characters and max 20 characters";
    if(password.length() < 6 || password.length() > 20) return "Invalid password must be at least 6 and max 20 characters";
    return null;
  }
}
