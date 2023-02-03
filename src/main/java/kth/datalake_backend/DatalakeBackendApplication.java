package kth.datalake_backend;

import kth.datalake_backend.Users.User;
import kth.datalake_backend.Users.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;


import java.util.List;

@SpringBootApplication
@EnableNeo4jRepositories
public class DatalakeBackendApplication {

   // private final static Logger log = LoggerFactory.getLogger(DatalakeBackendApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DatalakeBackendApplication.class, args);
    }


    @Bean
    CommandLineRunner demo(UserRepository personRepository) {
        return args -> {

            personRepository.deleteAll();

            User user1 = new User("Jonathan", "Lindqvist", "linkanjontes@gmail.com", "root");
            User user2 = new User("Viktor", "Lindström", "viktor@gmail.com", "root1");
            User user3 = new User("Kasper", "Lindström", "kasper@gmail.com", "root2");

            user1.setDatabases("KASPER.COM");
            user1.setDatabases("VIKTOR.COM");
            personRepository.save(user1);
            personRepository.save(user2);
            personRepository.save(user3);

            User test = personRepository.findByUsername(user1.getUsername());
            List<User> test2 = personRepository.findAll();
            System.out.println("FindByEmail(): " + test.toString());
            System.out.println("FindAll(): ");
            System.out.println(test2);

        };
    }
}
