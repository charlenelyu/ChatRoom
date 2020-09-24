package client;

import common.*;

import static client.config.ClientProperties.*;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * User interface responsible for handling user input.
 */
public class UserInterface {
    private BufferedReader stdIn;
    private String username;

    /**
     * Instantiates the user interface with specified standard input stream
     * and username.
     *
     * @param stdIn    standard input stream
     * @param username username of the client
     */
    public UserInterface(BufferedReader stdIn, String username) {
        this.stdIn = stdIn;
        this.username = username;
    }

    /**
     * Reads user input and converts it to a certain type of message.
     *
     * @return a certain type of message from chat room protocol
     * @throws IOException
     */
    public ChatRoomProtocol readUserInput() throws IOException {
        String input = stdIn.readLine();
        if (input.equals("quit")) return null; // deal with connection failure and disconnection

        if (input.equals(DISCONNECT_CMD)) {
            return new DisconnectMsg(username);
        } else if (input.equals(QUERY_USERS_CMD)) {
            return new QueryUsers(username);
        } else if (input.startsWith(BROADCAST_MESSAGE_CMD)) {
            String message = input.substring(4).trim();
            if (message.trim().isEmpty()) {
                System.out.println("Message cannot be empty!");
            } else {
                return new BroadcastMsg(username, message);
            }
        } else if (input.startsWith(DIRECT_MESSAGE_CMD)) {
            String[] msg = input.substring(1).split(" ", 2);
            if (msg.length != 2) {
                System.out.println("Message cannot be empty!");
            } else {
                return new DirectMsg(username, msg[0], msg[1]);
            }
        } else if (input.equals(HELP_CMD)) {
            sendCommandManual();
        } else {
            System.out.println("Invalid command! Enter '?' to review commands. ");
        }
        return null;
    }

    /**
     * Prints out command manual to the user.
     */
    public void sendCommandManual() {
        System.out.println("Command Manual:");
        System.out.println("-1: input 'who' to see all users in the chat room\n"
                + "-2: input '@username message_to_send' to send a direct message to the specified user\n"
                + "-3: input '@all message_to_send' to send a broadcast message to all users\n"
                + "-4: input 'logoff' to quit chat room\n"
                + "-5: input '?' to review all commands");
    }
}
