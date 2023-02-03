package kth.datalake_backend.Security.Services;


import kth.datalake_backend.Entity.User;
import kth.datalake_backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    System.out.println("JONTNNNNNNNNNNNNNNN" + user.toString());
    if (user==null) throw new UsernameNotFoundException("User not found with that username: " + username);
    return UserDetailsImp.build(user);
  }
}
