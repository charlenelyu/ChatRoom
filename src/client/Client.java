package client;

import common.ChatRoomProtocol;
import common.ConnectMsg;
import common.ConnectRes;

import static client.config.ClientProperties.*;
import static common.ChatRoomProtocol.*;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * A chat room client. A client needs to connect to the server first
 * with a unique username in order to join the chat room.
 */
public class Client {
    private String username;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader stdIn;
//    private CountDownLatch countDownLatch = new CountDownLatch(2);

    /**
     * Instantiates a client with the specified username.
     *
     * @param username username of the client
     * @throws IOException
     */
    public Client(String username) throws IOException {
        this.username = username;
        socket = new Socket(DEFAULT_IP, DEFAULT_PORT);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()), true);
        stdIn = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Connects to the server and starts two threads for sending and receiving messages.
     *
     * @throws IOException
     */
    public void start() throws IOException {
        if (!connect()) return;
        MsgSender msgSender = new MsgSender(stdIn, out, username);
        MsgReceiver msgReceiver = new MsgReceiver(in);
        msgSender.start();
        msgReceiver.start();
        try {
            msgReceiver.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        msgSender.setLogIn(false);
        try {
            msgSender.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        socket.close();
//        try {
//            countDownLatch.await();
//            // socket.close();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Sends connect message to the server and receives connect response.
     *
     * @return true if connection succeed, false otherwise.
     * @throws IOException
     */
    private boolean connect() throws IOException {
        ConnectMsg connectMsg = new ConnectMsg(username);
        out.println(connectMsg.toString());
        ChatRoomProtocol msg = MessageAnatomy(in.readLine());
        if (msg.getHeader() == CONNECT_RESPONSE) {
            ConnectRes connectRes = (ConnectRes) msg;
            System.out.println(connectRes.getMessage());
            return connectRes.isSuccess();
        } else {
            System.out.println("Wrong header!");
            return false;
        }
    }
}
