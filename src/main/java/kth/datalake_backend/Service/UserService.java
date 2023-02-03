package kth.datalake_backend.Service;

import kth.datalake_backend.Entity.User;
import kth.datalake_backend.Payload.Request.SignUpRequest;
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

import java.util.Collections;
import java.util.List;

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
  //Gör om denna till JWT snart
  public ResponseEntity authenticateUser(String username, String password) {
    System.out.println("test1");
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    System.out.println("test2");
    String jwt = jwtUtils.generateJwtToken(username);
    System.out.println("TOKEN" + jwt);
    UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
    System.out.println("apa");

    return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(),userDetails.getFirstName(),userDetails.getLastName()));
  }

  public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername()))
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));

    User user = new User(signUpRequest.getFirstname(), signUpRequest.getLastname(), signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()));
    //Lägga till roller här

    userRepository.save(user);
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  public List<User> getAllUser() {
    return userRepository.findAll();
  }
}
