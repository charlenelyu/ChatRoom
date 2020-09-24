package common;

/**
 * This class represents a response message to a disconnect request.
 */
public class DisconnectRes extends ChatRoomProtocol {
    private boolean success;
    private String message;

    /**
     * Instantiates a disconnect response with disconnect status and content.
     *
     * @param success disconnect status, true for success, false otherwise
     * @param message content of the message
     */
    public DisconnectRes(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Returns disconnect status.
     *
     * @return disconnect status
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Returns the identifier of the message type.
     *
     * @return identifier of the message type
     */
    @Override
    public int getHeader() {
        return DISCONNECT_RESPONSE;
    }

    /**
     * Returns message content to be printed on the console.
     *
     * @return message content to be printed on the console
     */
    @Override
    public String getMessage() {
        return success ? "Disconnect succeeded. " + message : "Disconnect failed. " + message;
    }

    /**
     * Returns a formatted string for data exchange between the server and clients.
     *
     * @return a formatted string for data exchange
     */
    @Override
    public String toString() {
        return DISCONNECT_RESPONSE + " " + success + " " + message;
    }
}
