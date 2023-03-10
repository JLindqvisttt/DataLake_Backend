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


/**
 * The DatalakeBackendApplication class is the main entry point for the application.
 * It is responsible for starting the Spring Boot application and configuring the necessary components.
 */
@SpringBootApplication
@EnableNeo4jRepositories
public class DatalakeBackendApplication {

    /**
     * The main method of the DatalakeBackendApplication class.
     * It is responsible for starting the Spring Boot application.
     *
     * @param args The command line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(DatalakeBackendApplication.class, args);
    }

    @Autowired
    PasswordEncoder encoder;

    /**
     * The preloadUsers method is responsible for preloading the user data into the database.
     * It creates and saves two users (an admin and a regular user) if they don't already exist in the database.
     *
     * @param userRepository The UserRepository interface that is used to access the database.
     * @return A CommandLineRunner instance that can be used to run the preloadUsers method.
     */
    @Bean
    public CommandLineRunner preloadUsers(UserRepository userRepository) {
        return (args) -> {
            if (userRepository.findByUsername("admin@gmail.com") == null)
                userRepository.save(new User("John", "Doe", "admin@gmail.com", encoder.encode("admin123"), ERole.valueOf("ROLE_ADMIN")));

            if (userRepository.findByUsername("user@gmail.com") == null)
                userRepository.save(new User("Jane", "Doe", "user@gmail.com", encoder.encode("user123"), ERole.valueOf("ROLE_USER")));
        };
    }
}


