package client;


import common.ChatRoomProtocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;

/**
 * A message sender responsible for reading user inputs and
 * sending corresponding messages to the server.
 */
public class MsgSender extends Thread {
    private BufferedReader stdIn;
    private PrintWriter out;
    private String username;
    private boolean logIn;
//    private CountDownLatch countDownLatch;

    /**
     * Instantiates the message sender with the specified standard input stream,
     * socket output stream and username
     * @param stdIn standard input stream for user input
     * @param out output stream of the socket
     * @param username username of the client
     */
    public MsgSender(BufferedReader stdIn, PrintWriter out, String username) {
        this.stdIn = stdIn;
        this.out = out;
        this.username = username;
        this.logIn = true;
    }

    /**
     * Sets login status
     * @param logIn new login status
     */
    public void setLogIn(boolean logIn) {
        this.logIn = logIn;
    }

    /**
     * Starts the thread. While logging in, keep reading user input and
     * sending corresponding messages out to the server.
     */
    @Override
    public void run() {
        UserInterface userInterface = new UserInterface(stdIn, username);
        userInterface.sendCommandManual();
        try {
            while (logIn) {
                ChatRoomProtocol msg = userInterface.readUserInput();
                if (msg != null) {
                    out.println(msg.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
