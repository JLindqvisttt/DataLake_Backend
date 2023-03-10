package kth.datalake_backend.Security.Services;


import kth.datalake_backend.Entity.User.User;
import kth.datalake_backend.Repository.User.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserDetailsServiceImp implements UserDetailsService interface and provides implementation for its methods.
 * It loads user details from the AuthRepository and provides functionality to check if the user is an admin.
 */
@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final AuthRepository authRepository;

    /**
     * Class constructor
     *
     * @param authRepository specify the authRepository to use
     */
    public UserDetailsServiceImp(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    /**
     * Loads user details from AuthRepository by username and returns UserDetailsImp.
     *
     * @param username The username of the user to retrieve details for.
     * @return UserDetailsImp object containing user details.
     * @throws UsernameNotFoundException Thrown when no user is found with the given username.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User not found with that username: " + username);
        return UserDetailsImp.build(user);
    }

    /**
     * Checks if the user with the given username is an admin.
     *
     * @param username The username of the user to check.
     * @return True if the user is an admin, false otherwise.
     * @throws UsernameNotFoundException Thrown when no user is found with the given username.
     */
    public Boolean ifUserIsAdmin(String username) throws UsernameNotFoundException {
        User user = authRepository.findByUsername(username);
        if (user.getRole().toString() == "ROLE_ADMIN") return true;
        return false;
    }
}
