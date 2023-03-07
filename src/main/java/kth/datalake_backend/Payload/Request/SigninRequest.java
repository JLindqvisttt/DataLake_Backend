package kth.datalake_backend.Payload.Request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SigninRequest {

  @NotBlank
  @Size(min = 2, max = 20)
  private String username;

  @NotBlank
  @Size(min = 2, max = 20)
  private String password;

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
}
