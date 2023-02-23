package kth.datalake_backend.Service;

import kth.datalake_backend.Entity.User.ERole;
import kth.datalake_backend.Entity.User.User;
import kth.datalake_backend.Payload.Request.RemoveUserRequest;
import kth.datalake_backend.Payload.Request.SignUpRequest;
import kth.datalake_backend.Payload.Request.UpdateUserRequest_Admin;
import kth.datalake_backend.Payload.Response.MessageResponse;
import kth.datalake_backend.Repository.User.AdminRepository;
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
    User userCheck = adminRepository.findByIdentity(updateUserRequest.getIdentity());
    if (userCheck == null) return ResponseEntity.badRequest().body(new MessageResponse("Could not save, try again"));
    if (updateUserRequest.getPassword() == null) {
      userCheck.setRole(updateUserRequest.getRole());
      userCheck.setAvailableDatabases(updateUserRequest.getAvailableDatabases());
    } else {
      userCheck.setPassword(encoder.encode(updateUserRequest.getPassword()));
      userCheck.setRole(updateUserRequest.getRole());
      userCheck.setAvailableDatabases(updateUserRequest.getAvailableDatabases());
    }
    adminRepository.save(userCheck);
    return ResponseEntity.ok(new MessageResponse("Successfully updated user"));
  }

  public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) {
    if (adminRepository.existsByUsername(signUpRequest.getUsername())) return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    User user = new User(signUpRequest.getFirstname(), signUpRequest.getLastname(), signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()), ERole.ROLE_USER);
    adminRepository.save(user);
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  public ResponseEntity removeUser(RemoveUserRequest removeUserRequest) {
    User user = adminRepository.findByIdentity(removeUserRequest.getIdentity());
    if (user == null) return ResponseEntity.badRequest().body( new MessageResponse("Could not remove user, try again"));
    adminRepository.delete(user);
    return ResponseEntity.ok(new MessageResponse("Successfully removed user"));
  }
}
