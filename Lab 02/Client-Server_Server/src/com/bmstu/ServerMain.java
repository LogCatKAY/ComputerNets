package com.bmstu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

// Лаба 2
// Простенький однопоточный сервер для приема сообщений от клиента и вывода текста в консоль

public class ServerMain {

    public void go() {

        try {
            ServerSocket serverSocket = new ServerSocket(8082);

            while(true) {
                Socket socket = serverSocket.accept();

                InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);

                String text = reader.readLine();
                System.out.println("Message from client: " + text);

                reader.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServerMain server = new ServerMain();
        server.go();
    }
}
