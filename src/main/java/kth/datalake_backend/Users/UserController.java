package kth.datalake_backend.Users;



import kth.datalake_backend.Payload.Request.SigninRequest;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity signIn(@Valid @RequestBody SigninRequest signinRequest) {
        return userService.authenticateUser(signinRequest.getUsername(), signinRequest.getPassword());
    }

    @GetMapping("/getAllUser")
    public List<User> getUserAllUsers() {
        return userService.getAllUser();
    }
}
