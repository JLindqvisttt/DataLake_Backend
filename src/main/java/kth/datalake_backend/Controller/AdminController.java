package kth.datalake_backend.Controller;


import kth.datalake_backend.Entity.User;
import kth.datalake_backend.Payload.Request.SignUpRequest;
import kth.datalake_backend.Payload.Request.UpdateUserRequest;
import kth.datalake_backend.Service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/admin")
public class AdminController {

  private final AdminService adminService;

  public AdminController(AdminService adminService){
    this.adminService = adminService;
  }

  @GetMapping("/getAllUser")
  public List<User> getUserAllUsers() {
    return adminService.getAllUser();
  }

  @PatchMapping("/updateUser")
  public ResponseEntity updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest){
    return adminService.updateUser(updateUserRequest);
  }
  @PostMapping("/signUp")
  public ResponseEntity signUp(@Valid @RequestBody SignUpRequest signUpRequestRequest){
    return adminService.registerUser(signUpRequestRequest);
  }
}
