package com.bmstu;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

// Лаба 2
// Простой клиент для отправки текстового сообщения на сервер.

public class ClientMain {

    public void go() {
        try {
            Scanner sc = new Scanner(System.in);
            String text = "";
            System.out.println("Input text for server or 0 for exit >>> ");

            while(!(text = sc.nextLine()).equals("0")) {
                Socket socket = new Socket("127.0.0.1", 8082);

                PrintWriter writer = new PrintWriter(socket.getOutputStream());

                writer.write(text);
                writer.close();
                System.out.println("Input text for server or 0 for exit >>> ");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
	ClientMain client = new ClientMain();
	client.go();
    }
}
