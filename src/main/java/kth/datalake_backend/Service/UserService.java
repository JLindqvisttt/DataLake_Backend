package kth.datalake_backend.Service;


import kth.datalake_backend.Entity.User.User;
import kth.datalake_backend.Payload.Request.UpdateUserPasswordRequest;
import kth.datalake_backend.Payload.Request.UpdateUserFirstnameAndLastnameRequest;
import kth.datalake_backend.Payload.Response.MessageResponse;
import kth.datalake_backend.Repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This class represents the service layer for user-related operations.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    /**
     * Updates the users first and lastname
     *
     * @param updateUserUsername UpdateUserFirstnameAndLastnameRequest {Long: Id,String: firstname, String lastname}
     * @return ResponseEntity<MessageResponse> Contains what happened to the request in message form
     */
    public ResponseEntity<MessageResponse> updateUserName(UpdateUserFirstnameAndLastnameRequest updateUserUsername) {
        Optional<User> getUser = userRepository.findById(updateUserUsername.getId());
        User userCheck;
        if (getUser.isPresent()) userCheck = getUser.get();
        else return ResponseEntity.badRequest().body(new MessageResponse("Could not find the user"));
        String control = updateUserUsername.check();
        if (control != null) return ResponseEntity.badRequest().body(new MessageResponse(control));

        userCheck.setFirstName(updateUserUsername.getFirstname());
        userCheck.setLastName(updateUserUsername.getLastname());

        userRepository.save(userCheck);
        return ResponseEntity.ok().body(new MessageResponse("Successfully update firstname and lastname"));

    }

    /**
     * Updates the users password by taking in old and new password
     *
     * @param updateUserPassword UpdateUserPasswordRequest{Long: Id, String: password, String: passwordCheck}
     * @return ResponseEntity<MessageResponse> Contains what happened to the request in message form
     */
    public ResponseEntity<MessageResponse> updateUserPassword(UpdateUserPasswordRequest updateUserPassword) {
        Optional<User> getUser = userRepository.findById(updateUserPassword.getId());
        User userCheck;
        if (getUser.isPresent()) userCheck = getUser.get();
        else return ResponseEntity.badRequest().body(new MessageResponse("Could not find the user"));
        String control = updateUserPassword.check();
        if (control != null) return ResponseEntity.badRequest().body(new MessageResponse(control));

        if (encoder.matches(updateUserPassword.getCheckPassword(), userCheck.getPassword())) {
            userCheck.setPassword(encoder.encode(updateUserPassword.getPassword()));
            userRepository.save(userCheck);
            return ResponseEntity.ok().body(new MessageResponse("Successfully update the password"));
        } else
            return ResponseEntity.badRequest().body(new MessageResponse("The old password is not correct, try again"));
    }
}
