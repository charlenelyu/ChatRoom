package common;

/**
 * This class represents a connect message.
 */
public class ConnectMsg extends ChatRoomProtocol {
    private String username;

    /**
     * Instantiates a connect message with the specified username.
     *
     * @param username username of the client to be connected
     */
    public ConnectMsg(String username) {
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
        return CONNECT_MESSAGE;
    }

    /**
     * Returns message content to be printed on the console.
     *
     * @return message content to be printed on the console
     */
    @Override
    public String getMessage() {
        return "User " + username + " is trying to connect to the server.";
    }

    /**
     * Returns a formatted string for data exchange between the server and clients.
     *
     * @return a formatted string for data exchange
     */
    @Override
    public String toString() {
        return CONNECT_MESSAGE + " " + username;
    }
}
