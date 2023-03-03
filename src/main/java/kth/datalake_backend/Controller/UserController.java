package kth.datalake_backend.Controller;

import kth.datalake_backend.Payload.Request.UserRequest.UpdateUserRequest_User;
import kth.datalake_backend.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PatchMapping("/updateUser")
  public ResponseEntity updateUser(@Valid @RequestBody UpdateUserRequest_User updateUserRequest_user) {
    return userService.updateUser(updateUserRequest_user);
  }



}
