package kth.datalake_backend.Payload.Response;

/**
 * ResponseBody for the message response returned by the server
 */
public class MessageResponse {

    private String message;

    /**
     * Class constructor with the specified message.
     *
     * @param message the message to set in the response
     */
    public MessageResponse(String message) {
        this.message = message;
    }

    /**
     * Get the message contained in this response
     *
     * @return  message contained in this response
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the message to be contained in this response
     *
     * @param message the message to set in this response
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
