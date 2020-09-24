package server;

import java.io.IOException;
import java.util.Scanner;

public class ServerRunner {
    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.start();
            String command = "";
            Scanner in = new Scanner(System.in);
            while (!command.equals("exit")) {
                command = in.nextLine();
            }
            server.exit();
        } catch (IOException e) {
            System.err.println("Error: failed to create a server, please try again.");;
        }
    }
}
