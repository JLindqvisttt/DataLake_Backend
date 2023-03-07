package kth.datalake_backend.Controller;


import kth.datalake_backend.Payload.Request.SignUpRequest;
import kth.datalake_backend.Payload.Request.SigninRequest;
import kth.datalake_backend.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
public class AuthController {

  public final AuthService authService;


  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/signIn")
  public ResponseEntity<?> signIn(@Valid @RequestBody SigninRequest signinRequest){
    return authService.authenticateUser(signinRequest.getUsername(),signinRequest.getPassword());
  }

}
