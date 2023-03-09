package kth.datalake_backend.Security.Services;


import kth.datalake_backend.Entity.User.User;
import kth.datalake_backend.Repository.User.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

  @Autowired
  AuthRepository authRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = authRepository.findByUsername(username);
    if (user==null) throw new UsernameNotFoundException("User not found with that username: " + username);
    return UserDetailsImp.build(user);
  }

  public Boolean ifUserIsAdmin(String username) throws UsernameNotFoundException {
    User user = authRepository.findByUsername(username);
    if(user.getRole().toString() == "ROLE_ADMIN") return true;
    return false;
  }
}
