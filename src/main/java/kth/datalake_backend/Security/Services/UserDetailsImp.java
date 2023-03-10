package kth.datalake_backend.Security.Services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kth.datalake_backend.Entity.User.ERole;
import kth.datalake_backend.Entity.User.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Implementation of UserDetails interface to hold user details required for authentication and authorization.
 */
public class UserDetailsImp implements UserDetails {

    private Long identity;
    private String username;
    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;

    private List<String> availableDatabases;
    private ERole roles;

    /**
     * Constructs a new UserDetailsImp object with the given user details.
     *
     * @param identity           the user's unique identity
     * @param username           the user's username
     * @param password           the user's password (will be ignored during serialization)
     * @param firstName          the user's first name
     * @param lastName           the user's last name
     * @param availableDatabases the list of databases available to the user
     * @param roles              the user's role
     */
    public UserDetailsImp(Long identity, String username, String password, String firstName, String lastName, List<String> availableDatabases, ERole roles) {
        this.identity = identity;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.availableDatabases = availableDatabases;
        this.roles = roles;
    }

    /**
     * Static method to build a UserDetailsImp object from a User object.
     *
     * @param user the User object to extract the user details from
     * @return a new UserDetailsImp object with the user details
     */
    public static UserDetailsImp build(User user) {
        return new UserDetailsImp(user.getId(), user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getAvailableDatasets(), user.getRole());
    }

    /**
     * Setter for the username field.
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setter for the password field.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for the identity field.
     *
     * @return the user's unique identity
     */
    public Long getIdentity() {
        return identity;
    }

    /**
     * Setter for the identity field.
     *
     * @param identity the new identity
     */
    public void setIdentity(Long identity) {
        this.identity = identity;
    }

    /**
     * Getter for the firstName field.
     *
     * @return the user's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for the firstName field.
     *
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for the lastName field.
     *
     * @return the user's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for the lastName field.
     *
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for the availableDatabases field.
     *
     * @return the list of databases available to the user
     */
    public List<String> getAvailableDatabases() {
        return availableDatabases;
    }

    /**
     * Setter for the availableDatabases field.
     *
     * @param availableDatabases the new list of available databases
     */
    public void setAvailableDatabases(List<String> availableDatabases) {
        this.availableDatabases = availableDatabases;
    }

    /**
     * Returns the user's role.
     *
     * @return the user's role.
     */
    public ERole getRoles() {
        return roles;
    }

    /**
     * Sets the user's role.
     *
     * @param roles the user's role to be set.
     */
    public void setRoles(ERole roles) {
        this.roles = roles;
    }

    /**
     * Returns the authorities granted to the user.
     *
     * @return the authorities granted to the user.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * Returns the user's password.
     *
     * @return the user's password.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the user's username.
     *
     * @return the user's username.
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Indicates whether the user's account has expired.
     *
     * @return true if the user's account is not expired, false otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or not.
     *
     * @return true if the user is not locked, false otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     *
     * @return true if the user's credentials are not expired, false otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     *
     * @return true if the user is enabled, false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

}
