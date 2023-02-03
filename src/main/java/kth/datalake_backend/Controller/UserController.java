package kth.datalake_backend.Controller;


import kth.datalake_backend.Entity.User;
import kth.datalake_backend.Payload.Request.SignUpRequest;
import kth.datalake_backend.Payload.Request.SigninRequest;
import kth.datalake_backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/signIn")
  public ResponseEntity signIn(@Valid @RequestBody SigninRequest signinRequest){
    return userService.authenticateUser(signinRequest.getUsername(),signinRequest.getPassword());
  }

  @PostMapping("/signUp")
  public ResponseEntity signUp(@Valid @RequestBody SignUpRequest signUpRequestRequest){
    return userService.registerUser(signUpRequestRequest);
  }

  @GetMapping("/getAllUser")
  public List<User> getUserAllUsers() {
    return userService.getAllUser();
  }
}
