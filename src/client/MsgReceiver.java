package client;

import common.ChatRoomProtocol;
import common.ConnectRes;
import common.DisconnectRes;

import static common.ChatRoomProtocol.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * A message receiver responsible for receiving messages from the server
 * and printing messages to the console.
 */
public class MsgReceiver extends Thread {
    private BufferedReader in;
    private boolean logIn;
//    private CountDownLatch countDownLatch;

    /**
     * Instantiates the message receiver with the specified input stream.
     *
     * @param in input stream of the socket.
     */
    public MsgReceiver(BufferedReader in) {
        this.in = in;
        this.logIn = true;
    }

    /**
     * Starts the thread. While logging in, keep reading messages from
     * the socket input stream and printing messages out to the console.
     */
    @Override
    public void run() {
        try {
            while (logIn) {
                ChatRoomProtocol msg = MessageAnatomy(in.readLine());
                int header = msg.getHeader();
                switch (header) {
                    case (CONNECT_RESPONSE):
                        ConnectRes connectRes = (ConnectRes) msg;
                        if (!connectRes.isSuccess()) {
                            logIn = false;
                            System.out.println(connectRes.getMessage());
                            System.out.println("Enter 'quit' to exit.");
                        } else {
                            System.out.println(connectRes.getMessage());
                        }
                        break;
                    case (DISCONNECT_RESPONSE):
                        DisconnectRes disconnectRes = (DisconnectRes) msg;
                        if (disconnectRes.isSuccess()) {
                            logIn = false;
                            System.out.println(disconnectRes.getMessage());
                            System.out.println("Enter 'quit' to exit. ");
                        } else {
                            System.out.println(disconnectRes.getMessage());
                        }
                        break;
                    default:
                        System.out.println(msg.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
