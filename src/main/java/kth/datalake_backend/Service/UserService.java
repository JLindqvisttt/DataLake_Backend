package kth.datalake_backend.Service;


import kth.datalake_backend.Entity.User;
import kth.datalake_backend.Payload.Request.UpdateUserRequest_Admin;
import kth.datalake_backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

  public ResponseEntity updateUser(UpdateUserRequest_Admin updateUserRequest) {
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
