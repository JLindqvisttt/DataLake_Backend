package kth.datalake_backend.Service;


import kth.datalake_backend.Entity.User.User;
import kth.datalake_backend.Payload.Request.UserRequest.UpdateUserRequest_User;
import kth.datalake_backend.Payload.Response.MessageResponse;
import kth.datalake_backend.Repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;
  @Autowired
  PasswordEncoder encoder;


  public ResponseEntity updateUser(UpdateUserRequest_User updateUserRequest_user) {
    User userCheck = userRepository.findByIdentity(updateUserRequest_user.getIdentity());
    if (userCheck == null) return ResponseEntity.badRequest().body(new MessageResponse("Could not save, try again"));
    String message = "";
    if (updateUserRequest_user.getPassword() == null) {
      userCheck.setFirstName(updateUserRequest_user.getFirstname());
      userCheck.setLastName(updateUserRequest_user.getLastname());
      message = "Successfully update firstname and lastname";
    } else {
      if (encoder.matches(updateUserRequest_user.getCheckPassword(), userCheck.getPassword())) {
        userCheck.setPassword(encoder.encode(updateUserRequest_user.getPassword()));
        message = "Successfully update the password";
      } else return ResponseEntity.badRequest().body(new MessageResponse("The old password is not correct, try again"));
    }
    userRepository.save(userCheck);
    return ResponseEntity.ok(new MessageResponse(message));
  }


}
