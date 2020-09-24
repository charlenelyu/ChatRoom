package common;

/**
 * This class represents a failure message.
 */
public class FailedMsg extends ChatRoomProtocol {
    private String message;

    /**
     * Instantiates a broadcast message with specified content.
     *
     * @param message content of the failure message
     */
    public FailedMsg(String message) {
        this.message = message;
    }

    /**
     * Returns the identifier of the message type.
     *
     * @return identifier of the message type
     */
    @Override
    public int getHeader() {
        return FAILED_MESSAGE;
    }

    /**
     * Returns message content to be printed on the console.
     *
     * @return message content to be printed on the console
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Returns a formatted string for data exchange between the server and clients.
     *
     * @return a formatted string for data exchange
     */
    @Override
    public String toString() {
        return FAILED_MESSAGE + " " + message;
    }
}
