package ru.netology;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private Server server;
    private Socket clientSocket = null;

    private PrintWriter outMessage;
    private Scanner inMassage;

    public ClientHandler(Socket socket, Server server) {
        try {
            this.server = server;
            this.clientSocket = socket;
            this.inMassage = new Scanner(socket.getInputStream());
            this.outMessage = new PrintWriter(socket.getOutputStream());
        } catch (Exception e) {

        }
    }

    @Override
    public void run() {

    }
}
