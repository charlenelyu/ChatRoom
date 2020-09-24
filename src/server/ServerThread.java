package server;

import common.*;

import static common.ChatRoomProtocol.*;
import static server.config.ServerProperties.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;

/**
 * A server thread handles one socket for communication between the server and one client.
 */
public class ServerThread implements Runnable {
    private Socket socket;
    private String username;
    private BufferedReader in;
    private PrintWriter out;
    private boolean connected;
    private ServerMsgProcessor msgProcessor;


    /**
     * Instantiates a server thread.
     *
     * @param socket       socket to communicate with the client
     * @param username     username of the client
     * @param in           input stream of the socket
     * @param out          output stream of the socket
     * @param msgProcessor instance of message processor
     * @throws IOException
     */
    public ServerThread(Socket socket, String username, BufferedReader in, PrintWriter out,
                        ServerMsgProcessor msgProcessor) throws IOException {
        this.socket = socket;
        socket.setSoTimeout(CONNECTION_TIMEOUT);
        this.username = username;
        this.in = in;
        this.out = out;
        this.connected = true;
        this.msgProcessor = msgProcessor;
    }

    /**
     * Starts the thread, communicates with the client by reading from and writing to the socket.
     */
    @Override
    public void run() {
        try {
            while (connected) {
                try {
                    ChatRoomProtocol msg = MessageAnatomy(in.readLine());
                    int header = msg.getHeader();
                    switch (header) {
                        case (DISCONNECT_MESSAGE):
                            disconnect();
                            break;
                        case (QUERY_CONNECTED_USERS):
                            List<String> users = msgProcessor.getUserList();
                            out.println(new QueryRes(users).toString());
                            break;
                        case (BROADCAST_MESSAGE):
                            BroadcastMsg broadcastMsg = (BroadcastMsg) msg;
                            msgProcessor.sendBroadcastMsg(broadcastMsg);
                            break;
                        case (DIRECT_MESSAGE):
                            DirectMsg directMsg = (DirectMsg) msg;
                            boolean result = msgProcessor.sendDirectMsg(directMsg);
                            if (!result) {
                                sendFailedMessage("Invalid recipient!");
                            }
                            break;
                        default:
                            sendFailedMessage("Undefined header!");
                    }
                } catch (SocketTimeoutException e) {
                    // e.printStackTrace();
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disconnects the client when receiving a disconnect message.
     */
    public void disconnect() {
        msgProcessor.removeUser(username);
        out.println(new DisconnectRes(true, "You are no longer connected.").toString());
        connected = false;
    }

    /**
     * Sends failed message.
     *
     * @param content the content of failed message
     */
    private void sendFailedMessage(String content) {
        out.println(new FailedMsg(content).toString());
    }

    public void sendMessage(String strWithHeader) {
        out.println(strWithHeader);
    }
}
