package kth.datalake_backend.Service;

import io.jsonwebtoken.Jwts;
import kth.datalake_backend.Entity.ERole;
import kth.datalake_backend.Entity.User;
import kth.datalake_backend.Payload.Request.SignUpRequest;
import kth.datalake_backend.Payload.Request.UpdateUserRequest;
import kth.datalake_backend.Payload.Response.JwtResponse;
import kth.datalake_backend.Payload.Response.MessageResponse;
import kth.datalake_backend.Repository.UserRepository;
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

import java.sql.SQLOutput;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;


    @Autowired
    JwtUtils jwtUtils;

    public ResponseEntity authenticateUser(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(username);
        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getIdentity(), userDetails.getUsername(), userDetails.getFirstName(), userDetails.getLastName(), userDetails.getRoles().toString(), userDetails.getAvailableDatabases().stream().toList()));
    }

    public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername()))
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));

        User user = new User(signUpRequest.getFirstname(), signUpRequest.getLastname(), signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()), ERole.ROLE_USER);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public ResponseEntity updateUser(UpdateUserRequest updateUserRequest) {

        if (userRepository.findByIdentity(updateUserRequest.getIdentity()) == null)
            return ResponseEntity.badRequest().body("User not found");

        User successUser = new User(updateUserRequest.getIdentity(),
                updateUserRequest.getFirstname(),
                updateUserRequest.getLastname(),
                updateUserRequest.getUsername(),
                encoder.encode(updateUserRequest.getPassword()),
                updateUserRequest.getAvailableDatabases(),
                updateUserRequest.getRole());
        userRepository.save(successUser);
        return ResponseEntity.ok(new MessageResponse("Successfully updated user"));
    }


}
