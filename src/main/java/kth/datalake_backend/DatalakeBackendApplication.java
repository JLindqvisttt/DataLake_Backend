
package kth.datalake_backend;

import kth.datalake_backend.Entity.User.ERole;
import kth.datalake_backend.Entity.User.User;
import kth.datalake_backend.Repository.User.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableNeo4jRepositories
public class DatalakeBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatalakeBackendApplication.class, args);
    }

    @Autowired
    PasswordEncoder encoder;

    @Bean
    public CommandLineRunner preloadUsers(UserRepository userRepository) {
        return (args) -> {
            User userAdmin = new User("John", "Doe", "admin@gmail.com", encoder.encode("admin"), ERole.valueOf("ROLE_ADMIN"));
            User user = new User("Jane", "Doe", "user@gmail.com", encoder.encode("user"), ERole.valueOf("ROLE_USER"));

            userRepository.save(userAdmin);
            userRepository.save(user);

        };
    }
}


