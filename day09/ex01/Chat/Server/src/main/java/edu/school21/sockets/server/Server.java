package edu.school21.sockets.server;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.services.MessageService;
import edu.school21.sockets.services.UsersService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Server {
    private final List<ClientHandler> clients = new LinkedList<>();
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private UsersService usersService;
    private MessageService messageService;

    public void start(int port) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
        usersService = context.getBean(UsersService.class);
        messageService = context.getBean(MessageService.class);

        serverSocket = new ServerSocket(port);
        System.out.println("The server is running.");
        while (true) {
            clientSocket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(this, clientSocket);
            clients.add(clientHandler);
            new Thread(clientHandler).start();
        }
    }

    public boolean signIn(String username, String password) {
        return usersService.signIn(username, password);
    }

    public boolean signUp(String username, String password) {
        return usersService.signUp(username, password);
    }

    public void sendMessageToAllClients(String msg, ClientHandler sender) {
        messageService.saveMessage(msg, sender.getUsername());
        for (ClientHandler o : clients) {
            if (o != sender) {
                o.sendMessage(sender.getUsername() + ": " + msg);
            }
        }
    }

    public void removeClient(ClientHandler clientHandler) {
        try {
            clientHandler.disconnect();
        } catch (IOException ignored) {
        }
        clients.remove(clientHandler);
    }

    public void stop() throws IOException {
        clientSocket.close();
        serverSocket.close();
    }
}