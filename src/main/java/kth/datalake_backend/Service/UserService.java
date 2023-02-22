package kth.datalake_backend.Service;


import kth.datalake_backend.Entity.User;
import kth.datalake_backend.Payload.Request.RemoveUserRequest;
import kth.datalake_backend.Payload.Request.UpdateUserRequest;
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

  public ResponseEntity removeUser(RemoveUserRequest removeUserRequest){
    User user = userRepository.findByIdentity(removeUserRequest.getIdentity());
    System.out.println("Getting identity: " + removeUserRequest.getIdentity());
    if (user == null) return ResponseEntity.badRequest().body("Could not remove user, try again");
    userRepository.delete(user);
    return ResponseEntity.ok("Successfully removed user");
  }

}
