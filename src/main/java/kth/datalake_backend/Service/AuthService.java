package kth.datalake_backend.Service;

import kth.datalake_backend.Payload.Response.JwtResponse;
import kth.datalake_backend.Repository.User.AuthRepository;
import kth.datalake_backend.Security.JWT.JwtUtils;
import kth.datalake_backend.Security.Services.UserDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Authentication service class for handling business logic for authentication and make call to the database
 */
@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    AuthRepository authRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;

    /**
     * Authenticated the user through login in reqest, check if password and user are correct and then returns JWToken and account information
     *
     * @param username of the user trying to log in
     * @param password of the user trying to log in
     * @return user information and token, or error requestbody if in log failed
     */
    public ResponseEntity<?> authenticateUser(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(username);
        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getIdentity(), userDetails.getUsername(), userDetails.getFirstName(), userDetails.getLastName(), userDetails.getRoles().toString(), userDetails.getAvailableDatabases().stream().toList()));
    }
}
