package kth.datalake_backend.Payload.Request.AdminRequest;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

public class RemoveUserRequest {

  @Id
  @GeneratedValue
  private Long identity;

  public Long getIdentity() {
    return identity;
  }

  @Override
  public String toString() {
    return "RemoveUserRequest{" +
      "identity=" + identity +
      '}';
  }
}
