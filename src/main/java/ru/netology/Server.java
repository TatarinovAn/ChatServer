package ru.netology;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static final int PORT = 8080;
    private ArrayList<ClientHandler> clients = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        System.out.println("Start chat");
            File file = new File("D://Chat/Setting");
            file.mkdirs();
            try (FileWriter writer = new FileWriter("D://Chat/Setting/setting.txt", false)) {
                writer.write(Integer.toString(PORT));
                writer.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                ) {


                }
            }
        }
    }
}
