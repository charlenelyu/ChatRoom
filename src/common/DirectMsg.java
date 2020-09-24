package common;

/**
 * This class represents a direct message.
 */
public class DirectMsg extends ChatRoomProtocol {
    private String sender;
    private String recipient;
    private String message;

    /**
     * Instantiates a direct message with specified sender, recipient and content.
     *
     * @param sender    the sender of the direct message
     * @param recipient the recipient of the direct message
     * @param message   the content of the direct message
     */
    public DirectMsg(String sender, String recipient, String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
    }

    /**
     * Returns the sender of the direct message.
     *
     * @return the sender of the direct message
     */
    public String getSender() {
        return sender;
    }

    /**
     * Returns the recipient of the direct message.
     *
     * @return the recipient of the direct message
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Returns the identifier of the message type.
     *
     * @return identifier of the message type
     */
    @Override
    public int getHeader() {
        return DIRECT_MESSAGE;
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
        return DIRECT_MESSAGE + " " + sender + " " + recipient + " " + message;
    }
}
