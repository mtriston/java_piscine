package edu.school21.sockets.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Server server;
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public String getUsername() {
        return username;
    }

    private String username;

    public ClientHandler(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            if (!logIn()) {
                server.removeClient(this);
                return;
            }
            sendMessage("Start messaging");
            while (true) {
                String message = in.readLine();
                if (message.equalsIgnoreCase("exit")) {
                    server.removeClient(this);
                    break;
                }
                server.sendMessageToAllClients(message, this);
            }

        } catch (Exception e) {
            server.removeClient(this);
        }
    }

    private boolean logIn() throws IOException {
        sendMessage("Hello from Server!");
        sendMessage("Enter '1' for signIn, '2' for signUp");
        int cmd = Integer.parseInt(getMessage());
        sendMessage("Enter username:");
        username = getMessage();
        sendMessage("Enter password:");
        String password = getMessage();
        if (cmd == 1) {
            if (server.signIn(username, password))
                return true;
            else
                sendMessage("Incorrect login or password.");
        } else if (cmd == 2) {
            if (server.signUp(username, password))
                return true;
            else
                sendMessage("This username already exists.");
        }
        return false;
    }

    public void sendMessage(String msg) {
        out.println(msg);
    }

    private String getMessage() throws IOException {
        return in.readLine();
    }

    public void disconnect() throws IOException {
        if (out != null)
            out.close();
        if (in != null)
            in.close();
        if (clientSocket.isConnected())
            clientSocket.close();
    }
}
