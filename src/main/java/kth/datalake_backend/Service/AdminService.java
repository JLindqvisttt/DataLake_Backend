package kth.datalake_backend.Service;

import kth.datalake_backend.Entity.User.ERole;
import kth.datalake_backend.Entity.User.User;
import kth.datalake_backend.Payload.Request.RemoveUserRequest;
import kth.datalake_backend.Payload.Request.SignUpRequest;
import kth.datalake_backend.Payload.Request.UpdateUserRequest_Admin;
import kth.datalake_backend.Payload.Response.MessageResponse;
import kth.datalake_backend.Repository.User.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Admin service for business logic and call to the database
 */
@Service
public class AdminService {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    PasswordEncoder encoder;

    /**
     * Returns all users from the database
     *
     * @return List<Users>
     */
    public List<User> getAllUser() {
        return adminRepository.findAll();
    }

    /**
     * Update a chosen user can update password, role and database access
     *
     * @param updateUserRequest UpdateUserRequest_Admin{String: username,
     *                          String: password,
     *                          String: firstname,
     *                          String:password,
     *                          List<String>: datasets}
     * @return ResponseEntity<MessageResponse> Contains what happened to the request in message form
     */
    public ResponseEntity<MessageResponse> updateUser(UpdateUserRequest_Admin updateUserRequest) {
        Optional<User> getUser = adminRepository.findById(updateUserRequest.getId());
        User userCheck;
        if (getUser.isPresent()) userCheck = getUser.get();
        else return ResponseEntity.badRequest().body(new MessageResponse("Could not find the user"));

        if (updateUserRequest.getPassword() != null) {
            if (updateUserRequest.getPassword().length() >= 6 && updateUserRequest.getPassword().length() <= 20)
                userCheck.setPassword(encoder.encode(updateUserRequest.getPassword()));
            else
                return ResponseEntity.badRequest().body(new MessageResponse("Is not a valid password, must be more the 6 and max 20 characters"));
        }
        if (updateUserRequest.getAvailableDatasets() != null)
            userCheck.setAvailableDatabases(updateUserRequest.getAvailableDatasets());
        if (updateUserRequest.getRole() != null) userCheck.setRole(updateUserRequest.getRole());

        adminRepository.save(userCheck);
        return ResponseEntity.ok(new MessageResponse("Successfully updated user"));
    }

    /**
     * Registers a user uses firstname, lastname, username and password
     *
     * @param signUpRequest SignUpRequest { String: firstname,
     *                      String: lastname,
     *                      String: password,
     *                      String: username}
     * @return ResponseEntity<MessageResponse> Contains what happened to the request in message form
     */
    public ResponseEntity<MessageResponse> registerUser(SignUpRequest signUpRequest) {
        String control = signUpRequest.check();
        if (control != null) return ResponseEntity.badRequest().body(new MessageResponse(control));
        if (adminRepository.existsByUsername(signUpRequest.getUsername()))
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));

        User user = new User(signUpRequest.getFirstname(), signUpRequest.getLastname(), signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()), ERole.ROLE_USER);

        adminRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

    /**
     * Deletes a user from the database using the ID
     *
     * @param removeUserRequest RemoveUserRequest {Long: Id}
     * @return ResponseEntity<MessageResponse> Contains what happened to the request in message form
     */
    public ResponseEntity<MessageResponse> removeUser(RemoveUserRequest removeUserRequest) {
        Optional<User> getUser = adminRepository.findById(removeUserRequest.getId());
        User user;
        if (getUser.isPresent()) user = getUser.get();
        else return ResponseEntity.badRequest().body(new MessageResponse("Could not find the user"));

        adminRepository.delete(user);

        return ResponseEntity.ok(new MessageResponse("Successfully removed user"));
    }

    /**
     * Returns the number of existing nodes in the database currently
     *
     * @return List<Long> contains 1 Long value
     */
    public List<Long> nrOfNodes() {
        return adminRepository.nrOfNodes();
    }

    /**
     * Returns the amount off relation that exists in the database
     *
     * @return List<Long> contains 1 Long value
     */
    public List<Long> nrOfRelations() {
        return adminRepository.nrOfRelations();
    }
}
