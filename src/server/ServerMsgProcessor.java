package server;

import common.BroadcastMsg;
import common.DirectMsg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Server message processor handles operations related to the clients map,
 * including get full list of users, remove a certain user from the map,
 * send broadcast messages and direct messages.
 */
public class ServerMsgProcessor {
    private Map<String, ServerThread> clients;

    /**
     * Instantiates a new server message processor with the reference to the clients map.
     *
     * @param clients the clients map
     */
    public ServerMsgProcessor(Map<String, ServerThread> clients) {
        this.clients = clients;
    }

    /**
     * Removes specified client from the map.
     *
     * @param username the username of the client
     */
    public void removeUser(String username) {
        clients.remove(username);
    }

    /**
     * Returns a full list of existing users.
     *
     * @return a list of usernames
     */
    public List<String> getUserList() {
        return new ArrayList<>(this.clients.keySet());
    }

    /**
     * Sends a broadcast message to all clients in the client map except the sender.
     *
     * @param msg a BroadcastMsg instance
     */
    public void sendBroadcastMsg(BroadcastMsg msg) {
        for (String username : clients.keySet()) {
            if (!msg.getSender().equals(username)) {
                clients.get(username).sendMessage(msg.toString());
            }
        }
    }

    /**
     * Sends a direct message to a certain client in the client map.
     *
     * @param msg a DirectMsg instance
     * @return true if success, false if the recipient is not in the user list
     */
    public boolean sendDirectMsg(DirectMsg msg) {
        if (!clients.containsKey(msg.getRecipient())) {
            return false;
        }
        clients.get(msg.getRecipient()).sendMessage(msg.toString());
        return true;
    }
}
