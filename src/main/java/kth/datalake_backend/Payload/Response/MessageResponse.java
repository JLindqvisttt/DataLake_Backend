package kth.datalake_backend.Payload.Response;

public class MessageResponse {

  private String message;
  public MessageResponse(String message){
    this.message = message;
  }

  public String getMessage(){
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return  message;
  }
}
