package ru.netology;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private int port;
    private ArrayList<ClientHandler> clients = new ArrayList<>();

    public void sendMessageToAllClient(String message) {
        for (ClientHandler entry : clients) {
            entry.sendMsg(message);
        }
    }
    public Server () {

    }

    public void start() {
        Socket clientSocket = null;

        StringBuffer sb = new StringBuffer();
        try (FileReader reader = new FileReader("setting.txt")) {
            int c;
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Ошибка в чтении");
        }

        this.port = Integer.parseInt(sb.toString());


        try (ServerSocket serverSocket = new ServerSocket(port)) // подключение сервера
        {
            System.out.println("Server Start");

            while (true) {
                clientSocket = serverSocket.accept();
                ClientHandler client = new ClientHandler(clientSocket, this);
                clients.add(client);
                System.out.println("Колличество клиентов: " + clients.size());
                new Thread(client).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }
}

