package kth.datalake_backend.Controller;


import kth.datalake_backend.Entity.User.User;
import kth.datalake_backend.Payload.Request.RemoveUserRequest;
import kth.datalake_backend.Payload.Request.SignUpRequest;
import kth.datalake_backend.Payload.Request.UpdateUserRequest_Admin;
import kth.datalake_backend.Payload.Response.MessageResponse;
import kth.datalake_backend.Service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * RestController for Admin
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/admin")
public class AdminController {

  private final AdminService adminService;

  /**
   * Class constructor specifying the adminservice to use
   * @param adminService adminservice
   */
  public AdminController(AdminService adminService){
    this.adminService = adminService;
  }

  /**
   * Get all users from database
   * @return a list of users
   */
  @GetMapping("/getAllUser")
  public List<User> getUserAllUsers() {
    return adminService.getAllUser();
  }

  /**
   * Update user
   * @param updateUserRequest the user to update
   * @return a ResponseEntity
   */
  @PatchMapping("/updateUser")
  public ResponseEntity<MessageResponse> updateUser(@Valid @RequestBody UpdateUserRequest_Admin updateUserRequest){
    return adminService.updateUser(updateUserRequest);
  }

  /**
   * Sign up a user
   * @param signUpRequestRequest a user to register
   * @return a ResponseEntity
   */
  @PostMapping("/signUp")
  public ResponseEntity<MessageResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequestRequest){
    return adminService.registerUser(signUpRequestRequest);
  }

  /**
   * Remove a user
   * @param removeUserRequest the user to remove
   * @return a ResponseEntity
   */
  @PostMapping("/removeUser")
  public ResponseEntity<MessageResponse> removeUser(@Valid @RequestBody RemoveUserRequest removeUserRequest) {
    return adminService.removeUser(removeUserRequest);
  }


  @GetMapping("/nrOfNodes")
  public List<Long> nrOfNodes() {
    return adminService.nrOfNodes();
  }


  @GetMapping("/nrOfRelations")
  public List<Long> nrOfRelations() {
    return adminService.nrOfRelations();
  }
}
