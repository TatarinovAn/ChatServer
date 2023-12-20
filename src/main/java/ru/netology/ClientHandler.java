package ru.netology;

import java.io.FileWriter;
import java.io.IOException;
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
            System.out.println(socket);
            this.inMassage = new Scanner(socket.getInputStream());
            this.outMessage = new PrintWriter(socket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            // сервер отправляет сообщение
            server.sendMessageToAllClient("Новый участник вошёл в чат!");


            while (true) {
                if (inMassage.hasNext()) {

                    String clientMessage = inMassage.nextLine();
                    if (clientMessage.contains("/exit")) {
                        break;
                    }

                    try (FileWriter writer = new FileWriter("message.txt", true)) {
                        writer.write(clientMessage);
                        writer.append('\n');
                        writer.flush();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    server.sendMessageToAllClient(clientMessage);
                }
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            this.close();
        }
    }

    public void sendMsg(String msg) {
        try {
            outMessage.println(msg);
            outMessage.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void close() {
        // удаляем клиента из списка
        server.removeClient(this);
        sendMsg("Покинул чат");
    }
}
