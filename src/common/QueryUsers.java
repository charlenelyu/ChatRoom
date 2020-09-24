package common;

/**
 * This class represents a query about list of users in the chat room.
 */
public class QueryUsers extends ChatRoomProtocol {
    private String username;

    /**
     * Instantiates the query message with the specified username.
     *
     * @param username user who made the query
     */
    public QueryUsers(String username) {
        this.username = username;
    }

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
        return QUERY_CONNECTED_USERS;
    }

    /**
     * Returns message content to be printed on the console.
     *
     * @return message content to be printed on the console
     */
    @Override
    public String getMessage() {
        return "User " + username + " is requesting user list.";
    }

    /**
     * Returns a formatted string for data exchange between the server and clients.
     *
     * @return a formatted string for data exchange
     */
    @Override
    public String toString() {
        return QUERY_CONNECTED_USERS + " " + username;
    }
}
