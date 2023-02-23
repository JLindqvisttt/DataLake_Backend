package kth.datalake_backend.Security.Services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kth.datalake_backend.Entity.User.ERole;
import kth.datalake_backend.Entity.User.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImp implements UserDetails {



  private Long identity;
  private String username;
  @JsonIgnore
  private String password;
  private String firstName;
  private String lastName;

  private List<String> availableDatabases;
  private ERole roles;

  public UserDetailsImp(Long identity, String username, String password, String firstName, String lastName, List<String> availableDatabases, ERole roles) {
    this.identity = identity;
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.availableDatabases = availableDatabases;
    this.roles = roles;
  }

  public static UserDetailsImp build(User user){

      return new UserDetailsImp(user.getIdentity(),user.getUsername(), user.getPassword(),user.getFirstName(),user.getLastName(),user.getAvailableDatabases(),user.getRole());
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Long getIdentity() {
    return identity;
  }

  public void setIdentity(Long identity) {
    this.identity = identity;
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

  public List<String> getAvailableDatabases() {
    return availableDatabases;
  }

  public void setAvailableDatabases(List<String> availableDatabases) {
    this.availableDatabases = availableDatabases;
  }

  public ERole getRoles() {
    return roles;
  }

  public void setRoles(ERole roles) {
    this.roles = roles;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
