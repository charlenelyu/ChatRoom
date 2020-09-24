package common;

import java.util.Arrays;

/**
 * A protocol that sets rules for the chat room.
 * There are 9 types of messages, and every message exchanged between
 * the server and clients should follow a standard format.
 * Formats depend on what kind of message is being sent,
 * but all should begin with a message identifier specifying its type.
 */
public abstract class ChatRoomProtocol {
    /**
     * Message identifiers for each type of messages.
     */
    public static final int CONNECT_MESSAGE = 11;
    public static final int CONNECT_RESPONSE = 12;
    public static final int DISCONNECT_MESSAGE = 13;
    public static final int DISCONNECT_RESPONSE = 14;
    public static final int QUERY_CONNECTED_USERS = 15;
    public static final int QUERY_USER_RESPONSE = 16;
    public static final int BROADCAST_MESSAGE = 17;
    public static final int DIRECT_MESSAGE = 18;
    public static final int FAILED_MESSAGE = 19;

    /**
     * Converts the specified formatted string to
     * an instance of the corresponding message type.
     *
     * @param strWithHeader a formatted string
     * @return an instance of one message type
     */
    public static ChatRoomProtocol MessageAnatomy(String strWithHeader) {
        String[] msg = strWithHeader.split(" ", 2);
        int header = Integer.parseInt(msg[0]);
        String content = msg[1];
        switch (header) {
            case (CONNECT_MESSAGE):
                return new ConnectMsg(content);
            case (CONNECT_RESPONSE):
                msg = content.split(" ", 2);
                return new ConnectRes(Boolean.parseBoolean(msg[0]), msg[1]);
            case (DISCONNECT_MESSAGE):
                return new DisconnectMsg(content);
            case (DISCONNECT_RESPONSE):
                msg = content.split(" ", 2);
                return new DisconnectRes(Boolean.parseBoolean(msg[0]), msg[1]);
            case (QUERY_CONNECTED_USERS):
                return new QueryUsers(content);
            case (QUERY_USER_RESPONSE):
                return new QueryRes(Arrays.asList(content.split(" ")));
            case (BROADCAST_MESSAGE):
                msg = content.split(" ", 2);
                return new BroadcastMsg(msg[0], msg[1]);
            case (DIRECT_MESSAGE):
                msg = content.split(" ", 3);
                return new DirectMsg(msg[0], msg[1], msg[2]);
            case (FAILED_MESSAGE):
                return new FailedMsg(content);
            default:
                return new FailedMsg("Undefined header!");
        }
    }

    /**
     * Returns the identifier of the message type.
     *
     * @return identifier of the message type
     */
    public abstract int getHeader();

    /**
     * Returns message content to be printed on the console.
     *
     * @return message content to be printed on the console
     */
    public abstract String getMessage();

    /**
     * Returns a formatted string for data exchange between the server and clients.
     *
     * @return a formatted string for data exchange
     */
    public abstract String toString();
}
