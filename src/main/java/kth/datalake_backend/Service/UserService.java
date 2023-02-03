package kth.datalake_backend.Service;

import kth.datalake_backend.Entity.User;
import kth.datalake_backend.Repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  //Gör om denna till JWT snart
  public ResponseEntity authenticateUser(String username, String password) {
    User user = userRepository.findByUsername(username);
    if (user != null && user.getPassword().equals(password)) {
      return ResponseEntity.ok(user);
    }
    return (ResponseEntity) ResponseEntity.notFound();
  }

  public List<User> getAllUser() {
    return userRepository.findAll();
  }
}
