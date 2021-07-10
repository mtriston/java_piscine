package edu.school21.sockets.app;

import edu.school21.sockets.client.Client;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1 || args[0].matches("--server-port=[0-9]")) {
            System.err.println("Invalid argument! Expected --server-port=8081");
            System.exit(-1);
        }
        Client client = new Client();
        Scanner scanner = new Scanner(System.in);
        try {
            client.startConnection("127.0.0.1", Integer.parseInt(args[0].substring("--server-port=".length())));
            System.out.println(client.getMessage());
            client.sendMessage(scanner.nextLine());
            System.out.println(client.getMessage());
            client.sendMessage(scanner.nextLine());
            System.out.println(client.getMessage());
            client.sendMessage(scanner.nextLine());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.stopConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
