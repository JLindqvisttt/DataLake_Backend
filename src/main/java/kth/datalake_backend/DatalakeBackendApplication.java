package kth.datalake_backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories
public class DatalakeBackendApplication {


  public static void main(String[] args){
    SpringApplication.run(DatalakeBackendApplication.class, args);
  }




}
