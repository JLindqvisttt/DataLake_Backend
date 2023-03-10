package kth.datalake_backend.Controller;

import kth.datalake_backend.Payload.Request.UpdateUserPasswordRequest;
import kth.datalake_backend.Payload.Request.UpdateUserFirstnameAndLastnameRequest;
import kth.datalake_backend.Payload.Response.MessageResponse;
import kth.datalake_backend.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * RestController for Users
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    /**
     * Class constructor
     *
     * @param userService specify the userservice to use
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * Update the name of a user
     *
     * @param updateUserFirstnameAndLastnameRequest user to update
     * @return a ResponseEntity
     */
    @PatchMapping("/updateUserName")
    public ResponseEntity<MessageResponse> updateUserName(@Valid @RequestBody UpdateUserFirstnameAndLastnameRequest updateUserFirstnameAndLastnameRequest) {
        return userService.updateUserName(updateUserFirstnameAndLastnameRequest);
    }

    /**
     * Update the password of a user
     *
     * @param updateUserPasswordRequest the user to update
     * @return a ResponseEntity
     */
    @PatchMapping("/updateUserPassword")
    public ResponseEntity<MessageResponse> updateUserPassword(@Valid @RequestBody UpdateUserPasswordRequest updateUserPasswordRequest) {
        return userService.updateUserPassword(updateUserPasswordRequest);
    }

}
