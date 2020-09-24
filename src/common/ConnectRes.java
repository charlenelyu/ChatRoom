package common;

/**
 * This class represents a response message to a connect request.
 */
public class ConnectRes extends ChatRoomProtocol {
    private boolean success;
    private String message;

    /**
     * Instantiates a connect response message with connect status and content.
     *
     * @param success connect status, true for connect success, false otherwise
     * @param message content of the message
     */
    public ConnectRes(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Returns connect status.
     *
     * @return connect status
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
        return CONNECT_RESPONSE;
    }

    /**
     * Returns message content to be printed on the console.
     *
     * @return message content to be printed on the console
     */
    @Override
    public String getMessage() {
        return success ? "Connect succeeded. " + message : "Connect failed. " + message;
    }

    /**
     * Returns a formatted string for data exchange between the server and clients.
     *
     * @return a formatted string for data exchange
     */
    @Override
    public String toString() {
        return CONNECT_RESPONSE + " " + success + " " + message;
    }
}
