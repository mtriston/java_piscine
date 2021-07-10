package edu.school21.sockets.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    Scanner scanner = new Scanner(System.in);
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private volatile boolean CONTINUE = true;

    public void start(String ip, int port) throws IOException, InterruptedException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        Thread reader = new Thread(() -> {
            try {
                getMessage();
            } catch (IOException ignored) {
            }
        });

        Thread sender = new Thread(() -> {
            try {
                sendMessage();
            } catch (IOException ignored) {
            }
        });

        reader.start();
        sender.start();
        reader.join();
    }

    public void getMessage() throws IOException {
        while (CONTINUE) {
            String msg = in.readLine();
            if (msg == null) {
                CONTINUE = false;
                break;
            }
            System.out.println(msg);
        }
    }

    public void sendMessage() throws IOException {
        while (CONTINUE) {
            if (scanner.hasNext())
                out.println(scanner.nextLine());
        }
    }

    public void stopConnection() throws IOException {
        if (in != null)
            in.close();
        if (out != null)
            out.close();
        if (clientSocket != null)
            clientSocket.close();
        scanner.close();
    }
}
