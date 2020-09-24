package common;

import java.util.List;

/**
 * This class represents a response to user list query.
 */
public class QueryRes extends ChatRoomProtocol {
    private List<String> userList;

    /**
     * Instantiates a query response with specified user list.
     *
     * @param userList list of all users in the chat room
     */
    public QueryRes(List<String> userList) {
        this.userList = userList;
    }

    /**
     * Returns the user list.
     *
     * @return the user list
     */
    public List<String> getUserList() {
        return userList;
    }

    /**
     * Returns the identifier of the message type.
     *
     * @return identifier of the message type
     */
    @Override
    public int getHeader() {
        return QUERY_USER_RESPONSE;
    }

    /**
     * Returns message content to be printed on the console.
     *
     * @return message content to be printed on the console
     */
    @Override
    public String getMessage() {
        StringBuilder message = new StringBuilder(
                "There are " + (userList.size()) + " user(s) in the chat room: ");
        for (int i = 0; i < this.userList.size(); i++) {
            message.append("\n").append(i + 1).append(". ").append(userList.get(i));
        }
        return message.toString();
    }

    /**
     * Returns a formatted string for data exchange between the server and clients.
     *
     * @return a formatted string for data exchange
     */
    @Override
    public String toString() {
        return QUERY_USER_RESPONSE + " " + String.join(" ", userList);
    }
}
