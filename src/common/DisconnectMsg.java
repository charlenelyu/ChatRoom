package common;

/**
 * This class represents a disconnect message.
 */
public class DisconnectMsg extends ChatRoomProtocol {
    private String username;

    /**
     * Instantiates a disconnect message with the specified username.
     *
     * @param username username of the client to be disconnected
     */
    public DisconnectMsg(String username) {
        this.username = username;
    }

    /**
     * Returns the username of the client.
     *
     * @return the username of the client
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the identifier of the message type.
     *
     * @return identifier of the message type
     */
    @Override
    public int getHeader() {
        return DISCONNECT_MESSAGE;
    }

    /**
     * Returns message content to be printed on the console.
     *
     * @return message content to be printed on the console
     */
    @Override
    public String getMessage() {
        return "User " + username + " is trying to disconnect.";
    }

    /**
     * Returns a formatted string for data exchange between the server and clients.
     *
     * @return a formatted string for data exchange
     */
    @Override
    public String toString() {
        return DISCONNECT_MESSAGE + " " + username;
    }
}
