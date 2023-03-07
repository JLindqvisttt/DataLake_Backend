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
import java.util.Optional;

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
    Optional<User> getUser = adminRepository.findById(updateUserRequest.getId());
    User userCheck;
    if (getUser.isPresent()) userCheck = getUser.get();
    else return ResponseEntity.badRequest().body(new MessageResponse("Could not find the user"));

    if (updateUserRequest.getPassword() != null && updateUserRequest.getPassword().length() >= 2 && updateUserRequest.getPassword().length() <= 20) userCheck.setPassword(encoder.encode(updateUserRequest.getPassword()));
    if (updateUserRequest.getAvailableDatabases() != null) userCheck.setAvailableDatabases(updateUserRequest.getAvailableDatabases());
    if (updateUserRequest.getRole() != null) userCheck.setRole(updateUserRequest.getRole());
    adminRepository.save(userCheck);
    return ResponseEntity.ok(new MessageResponse("Successfully updated user"));
  }

  public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) {
    if (adminRepository.existsByUsername(signUpRequest.getUsername()))
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    User user = new User(signUpRequest.getFirstname(), signUpRequest.getLastname(), signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()), ERole.ROLE_USER);
    adminRepository.save(user);
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  public ResponseEntity removeUser(RemoveUserRequest removeUserRequest) {
    Optional<User> getUser = adminRepository.findById(removeUserRequest.getId());
    User user;
    if (getUser.isPresent()) user = getUser.get();
    else return ResponseEntity.badRequest().body(new MessageResponse("Could not find the user"));
    adminRepository.delete(user);
    return ResponseEntity.ok(new MessageResponse("Successfully removed user"));
  }

  public List<Long> nrOfNodes() {
    return adminRepository.nrOfNodes();
  }

  public List<Long> nrOfRelations() {
    return adminRepository.nrOfRelations();
  }
}
