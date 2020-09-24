package common;

/**
 * This class represents a broadcast message.
 */
public class BroadcastMsg extends ChatRoomProtocol {
    private String sender;
    private String message;

    /**
     * Instantiates a broadcast message with specified sender and content.
     *
     * @param sender  the sender of the broadcast message
     * @param message the content of the message
     */
    public BroadcastMsg(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    /**
     * Returns the sender of the broadcast message
     *
     * @return the sender of the broadcast message
     */
    public String getSender() {
        return sender;
    }

    /**
     * Returns the identifier of the message type.
     *
     * @return identifier of the message type
     */
    @Override
    public int getHeader() {
        return BROADCAST_MESSAGE;
    }

    /**
     * Returns message content to be printed on the console.
     *
     * @return message content to be printed on the console
     */
    @Override
    public String getMessage() {
        return sender + ": " + message;
    }

    /**
     * Returns a formatted string for data exchange between the server and clients.
     *
     * @return a formatted string for data exchange
     */
    @Override
    public String toString() {
        return BROADCAST_MESSAGE + " " + sender + " " + message;
    }
}
