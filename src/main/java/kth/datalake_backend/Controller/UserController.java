package kth.datalake_backend.Controller;

import kth.datalake_backend.Payload.Request.UpdateUserRequest_User;
import kth.datalake_backend.Payload.Response.MessageResponse;
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
  public ResponseEntity<MessageResponse> updateUser(@Valid @RequestBody UpdateUserRequest_User updateUserRequest_user) {
    return userService.updateUser(updateUserRequest_user);
  }

  @PatchMapping("/updateUserName")
  public ResponseEntity<MessageResponse> updateUserName(@Valid @RequestBody UpdateUserRequest_User updateUserRequest_user) {
    return userService.updateUser(updateUserRequest_user);
  }

  @PatchMapping("/updateUserPassword")
  public ResponseEntity<MessageResponse> updateUserPassword(@Valid @RequestBody UpdateUserRequest_User updateUserRequest_user) {
    return userService.updateUser(updateUserRequest_user);
  }

}
