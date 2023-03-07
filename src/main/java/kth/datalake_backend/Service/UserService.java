package kth.datalake_backend.Service;


import kth.datalake_backend.Entity.User.User;
import kth.datalake_backend.Payload.Request.UpdateUserRequest_User;
import kth.datalake_backend.Payload.Response.MessageResponse;
import kth.datalake_backend.Repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;


    public ResponseEntity<MessageResponse> updateUser(UpdateUserRequest_User updateUserRequest_user) {
        Optional<User> getUser = userRepository.findById(updateUserRequest_user.getId());
        User userCheck;
        if (getUser.isPresent()) userCheck = getUser.get();
        else return ResponseEntity.badRequest().body(new MessageResponse("Could not find the user"));

        String message = "";
        if (updateUserRequest_user.getPassword() == null) {
            if(updateUserRequest_user.getLastname().length() >= 2 && updateUserRequest_user.getLastname().length() <= 20 && updateUserRequest_user.getFirstname().length() >= 2 &&  updateUserRequest_user.getFirstname().length() <= 20){
                userCheck.setFirstName(updateUserRequest_user.getFirstname());
                userCheck.setLastName(updateUserRequest_user.getLastname());
                message = "Successfully update firstname and lastname";
            } else return ResponseEntity.badRequest().body(new MessageResponse("First name and last name must be latest 2 characters and max 20"));
        } else {
            if (encoder.matches(updateUserRequest_user.getCheckPassword(), userCheck.getPassword())) {
                if (updateUserRequest_user.getPassword().length() >= 6 && updateUserRequest_user.getPassword().length() <= 20) {
                    userCheck.setPassword(encoder.encode(updateUserRequest_user.getPassword()));
                    message = "Successfully update the password";
                } else return ResponseEntity.badRequest().body(new MessageResponse("Is not a valid password, must be more the 6 and max 20 characters"));
            } else return ResponseEntity.badRequest().body(new MessageResponse("The old password is not correct, try again"));
        }
        userRepository.save(userCheck);
        return ResponseEntity.ok(new MessageResponse(message));
    }


    public ResponseEntity<MessageResponse> updateUserName(UpdateUserRequest_User updateUserRequest_user) {
        Optional<User> getUser = userRepository.findById(updateUserRequest_user.getId());
        User userCheck;
        if (getUser.isPresent()) userCheck = getUser.get();
        else return ResponseEntity.badRequest().body(new MessageResponse("Could not find the user"));
        String control = updateUserRequest_user.checkName();
        if(control == null) return ResponseEntity.badRequest().body(new MessageResponse(control));
        System.out.println("UserName"+updateUserRequest_user.getFirstname().length());
        if (updateUserRequest_user.getLastname().length() >= 2 && updateUserRequest_user.getLastname().length() <= 20 && updateUserRequest_user.getFirstname().length() >= 2 &&  updateUserRequest_user.getFirstname().length() <= 20) {
            userCheck.setFirstName(updateUserRequest_user.getFirstname());
            userCheck.setLastName(updateUserRequest_user.getLastname());
            userRepository.save(userCheck);
            return ResponseEntity.ok().body(new MessageResponse("Successfully update firstname and lastname"));
        } else
            return ResponseEntity.badRequest().body(new MessageResponse("First name and last name must be latest 2 characters and max 20"));
    }

    public ResponseEntity<MessageResponse> updateUserPassword(UpdateUserRequest_User updateUserRequest_user) {
        Optional<User> getUser = userRepository.findById(updateUserRequest_user.getId());
        User userCheck;
        if (getUser.isPresent()) userCheck = getUser.get();
        else return ResponseEntity.badRequest().body(new MessageResponse("Could not find the user"));
        String control = updateUserRequest_user.checkName();
        if(control == null) return ResponseEntity.badRequest().body(new MessageResponse(control));
        if (encoder.matches(updateUserRequest_user.getCheckPassword(), userCheck.getPassword())) {
            if (updateUserRequest_user.getPassword().length() >= 6 && updateUserRequest_user.getPassword().length() <= 20) {
                userCheck.setPassword(encoder.encode(updateUserRequest_user.getPassword()));
                userRepository.save(userCheck);
                return ResponseEntity.ok().body(new MessageResponse("Successfully update the password"));
            }
            else return ResponseEntity.badRequest().body(new MessageResponse("Is not a valid password, must be more the 6 and max 20 characters"));
        }
        else return ResponseEntity.badRequest().body(new MessageResponse("The old password is not correct, try again"));
    }
}
