package server;

import common.BroadcastMsg;
import common.ChatRoomProtocol;
import common.ConnectMsg;
import common.ConnectRes;

import static common.ChatRoomProtocol.*;
import static server.config.ServerProperties.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * Chat room server, response to clients' connect request.
 */
public class Server extends Thread {
    private Boolean running;
    private ServerSocket serverSocket;
    private Map<String, ServerThread> clients;
    private ExecutorService pool;
    private ServerMsgProcessor msgProcessor;

    /**
     * Instantiates a new chat room server.
     *
     * @throws IOException
     */
    public Server() throws IOException {
        serverSocket = new ServerSocket(DEFAULT_PORT);
        serverSocket.setSoTimeout(ACCEPT_TIMEOUT); // set time out for accept()
        clients = new ConcurrentHashMap<>();
        pool = Executors.newFixedThreadPool(MAX_CLIENTS);
        msgProcessor = new ServerMsgProcessor(clients);
        // start();
    }

    /**
     * Starts the server. While the server is running,
     * it will be listening on specific port and
     * accepting connections made to the server socket.
     */
    @Override
    public void run() {
//        int port = serverSocket.getLocalPort();
//        System.out.println("The server is listening on port " + port + ". ");
        System.out.println("The server is running now.");
        running = true;
        while (running) {
            try {
                Socket socket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()), true);
                ChatRoomProtocol msg = MessageAnatomy(in.readLine());
                if (msg.getHeader() == CONNECT_MESSAGE) {
                    String username = ((ConnectMsg) msg).getUsername();
                    addClient(socket, in, out, username);
                } else {
                    sendConnectRes(out, false, "Wrong header!");
                }
            } catch (SocketTimeoutException e) {
                // e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Stops the server, shut down all running threads and send clients disconnect message.
     */
    public void exit() {
        pool.shutdown();
        for (ServerThread serverThread : clients.values()) {
            serverThread.disconnect();
        }
        running = false;
        try {
            pool.awaitTermination(TERMINATE_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds new client to the clients map and starts a new thread when
     * a connection is successfully made.
     * A connection is successful if the chat room doesn't reach its capacity
     * and the username is valid.
     * If connection fails, send connect failure message.
     *
     * @param socket   new socket returned by accept() method for client communication
     * @param in       input stream of the socket
     * @param out      output stream of the socket
     * @param username username of the new client
     * @throws IOException
     */
    private void addClient(Socket socket, BufferedReader in, PrintWriter out, String username)
            throws IOException {
        if (clients.size() >= MAX_CLIENTS) {
            sendConnectRes(out, false, "Server has reached its capacity. Connection rejected.");
            return;
        }
        if (clients.containsKey(username) || username.equals("all")) {
            sendConnectRes(out, false, "Invalid username.");
            return;
        }
        ServerThread newThread = new ServerThread(socket, username, in, out, msgProcessor);
        pool.execute(newThread);
        clients.put(username, newThread);
        sendConnectRes(out, true, "There are " + clients.size() + " connected users.");
        msgProcessor.sendBroadcastMsg(new BroadcastMsg(username, "I'm in the chat room now!"));
    }

    /**
     * Sends response to the client's connect request.
     *
     * @param out    output stream for client communication
     * @param status connection status, true if connection successes, false otherwise
     * @param msg    connection response message
     */
    private void sendConnectRes(PrintWriter out, boolean status, String msg) {
        ConnectRes connectRes = new ConnectRes(status, msg);
        out.println(connectRes.toString());
    }
}
