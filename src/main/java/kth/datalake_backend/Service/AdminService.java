package kth.datalake_backend.Service;


import kth.datalake_backend.Entity.ERole;
import kth.datalake_backend.Entity.User;
import kth.datalake_backend.Payload.Request.RemoveUserRequest;
import kth.datalake_backend.Payload.Request.SignUpRequest;
import kth.datalake_backend.Payload.Request.UpdateUserRequest_Admin;
import kth.datalake_backend.Payload.Response.MessageResponse;
import kth.datalake_backend.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
  @Autowired
  AdminRepository adminRepository;

  @Autowired
  PasswordEncoder encoder;
  public List<User> getAllUser() {
    return adminRepository.findAll();
  }

  public ResponseEntity updateUser(UpdateUserRequest_Admin updateUserRequest) {
    if (adminRepository.findByIdentity(updateUserRequest.getIdentity()) == null)
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
    adminRepository.save(successUser);
    return ResponseEntity.ok("Successfully updated user");
  }

  public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) {
    if (adminRepository.existsByUsername(signUpRequest.getUsername()))
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));

    User user = new User(signUpRequest.getFirstname(), signUpRequest.getLastname(), signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()), ERole.ROLE_USER);
    adminRepository.save(user);
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  public ResponseEntity removeUser(RemoveUserRequest removeUserRequest){
    User user = adminRepository.findByIdentity(removeUserRequest.getIdentity());
    System.out.println("Getting identity: " + removeUserRequest.getIdentity());
    if (user == null) return ResponseEntity.badRequest().body("Could not remove user, try again");
    adminRepository.delete(user);
    return ResponseEntity.ok("Successfully removed user");
  }
}
