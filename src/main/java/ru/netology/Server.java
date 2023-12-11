package ru.netology;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static final int PORT = 8080;
    private ArrayList<ClientHandler> clients = new ArrayList<>();

    public Server() {
        Socket clientSocket = null;
        File file = new File("D://Chat/Setting"); // Создание файла настроек
        file.mkdirs();
        try (FileWriter writer = new FileWriter("D://Chat/Setting/setting.txt", false)) {
            writer.write(Integer.toString(PORT));
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }


        try (ServerSocket serverSocket = new ServerSocket(PORT)) // подключение сервера
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

    public void sendMessageToAllClient(String message) {
        for (ClientHandler entry : clients) {
            entry.sendMsg(message);
        }
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }
}

