package kth.datalake_backend.Controller;

import kth.datalake_backend.Payload.Request.UpdateUserPasswordRequest;
import kth.datalake_backend.Payload.Request.UpdateUserUsernameRequest;
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


  @PatchMapping("/updateUserName")
  public ResponseEntity<MessageResponse> updateUserName(@Valid @RequestBody UpdateUserUsernameRequest updateUserUsernameRequest) {
    return userService.updateUserName(updateUserUsernameRequest);
  }

  @PatchMapping("/updateUserPassword")
  public ResponseEntity<MessageResponse> updateUserPassword(@Valid @RequestBody UpdateUserPasswordRequest updateUserPasswordRequest) {
    return userService.updateUserPassword(updateUserPasswordRequest);
  }

}
