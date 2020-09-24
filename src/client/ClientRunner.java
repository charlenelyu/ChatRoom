package client;

import java.io.IOException;
import java.util.Scanner;

public class ClientRunner {
    public static void main(String[] args) {
        try {
            System.out.println("Please enter your username: ");
            Scanner in = new Scanner(System.in);
            String username = "";
            while (username.isEmpty()) {
                username = in.nextLine();
            }
            Client client = new Client(username);
            client.start();
        } catch (IOException e) {
            System.err.println("Error: failed to create a client, please try again.");;
        }
    }
}