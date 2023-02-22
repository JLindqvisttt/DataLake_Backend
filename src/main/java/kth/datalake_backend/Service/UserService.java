package kth.datalake_backend.Service;


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

import java.util.List;

@Service
public class UserService {
  
  @Autowired
  UserRepository userRepository;
  @Autowired
  PasswordEncoder encoder;


  public List<User> getAllUser() {
    return userRepository.findAll();
  }

  public ResponseEntity updateUser(UpdateUserRequest updateUserRequest) {
    if (userRepository.findByIdentity(updateUserRequest.getIdentity()) == null)
      return ResponseEntity.badRequest().body("Could not save, try again");
    User successUser = null;
    if (updateUserRequest.getPassword() == null) {
      successUser = new User(updateUserRequest.getIdentity(),
        updateUserRequest.getFirstname(),
        updateUserRequest.getLastname(),
        updateUserRequest.getUsername(),
        updateUserRequest.getAvailableDatabases(),
        updateUserRequest.getRole());
    } else {
      successUser = new User(updateUserRequest.getIdentity(),
        updateUserRequest.getFirstname(),
        updateUserRequest.getLastname(),
        updateUserRequest.getUsername(),
        encoder.encode(updateUserRequest.getPassword()),
        updateUserRequest.getAvailableDatabases(),
        updateUserRequest.getRole());
    }
    userRepository.save(successUser);
    return ResponseEntity.ok("Successfully updated user");
  }
}
