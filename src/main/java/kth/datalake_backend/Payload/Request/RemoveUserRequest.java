package kth.datalake_backend.Payload.Request;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

public class RemoveUserRequest {

  @Id
  @GeneratedValue
  private Long Id;

  public Long getId() {
    return Id;
  }

  @Override
  public String toString() {
    return "RemoveUserRequest{" +
      "identity=" + Id +
      '}';
  }
}
