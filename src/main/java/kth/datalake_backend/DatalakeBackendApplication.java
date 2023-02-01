package kth.datalake_backend;

import kth.datalake_backend.Entity.User;
import kth.datalake_backend.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@SpringBootApplication
@EnableNeo4jRepositories
public class DatalakeBackendApplication {

  private final static Logger log = LoggerFactory.getLogger(DatalakeBackendApplication.class);

  public static void main(String[] args) throws Exception {
    SpringApplication.run(DatalakeBackendApplication.class, args);
    System.exit(0);
  }

  @Bean
  CommandLineRunner demo(UserRepository personRepository) {
    return args -> {

      personRepository.deleteAll();

      User user1 = new User("Jonathan","Lindqvist", "linkanjontes@gmail.com","root");
      User user2 = new User("Viktor","Lindström", "viktor@gmail.com","root1");
      User user3 = new User("Kasper","Lindström", "kasper@gmail.com","root2");

      personRepository.save(user1);
      personRepository.save(user2);
      personRepository.save(user3);

      User test = personRepository.findByEmail(user1.getEmail());
      List<User> test2 = personRepository.findAll();
      System.out.println("FindByEmail(): " +  test.toString());
      System.out.println("FindAll(): ");
      System.out.println(test2);

    };
  }

}
