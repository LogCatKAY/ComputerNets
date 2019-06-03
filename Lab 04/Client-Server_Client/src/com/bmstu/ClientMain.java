package com.bmstu;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Scanner;

// Лаба 4
// Простой клиент для отправки файла на сервер.

public class ClientMain {

    private static final String fileName = "file.txt";

    public void sendFile() {
        try {
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file);


            int fileSize = (int) file.length();
            byte[] byteArray = new byte[fileSize];

            Socket socket = new Socket("127.0.0.1", 9000);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            while(true) {
                out.writeLong(file.length());   //отсылаем размер
                out.writeUTF(file.getName());   //отсылаем имя файла
                int bytesToSend = fis.read(byteArray);

                if(bytesToSend == -1) {
                    break;
                }

                if(bytesToSend > 0) {
                    out.write(byteArray, 0, bytesToSend);
                }
            }
            fis.close();
            out.close();
            socket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FileGenerator fg = new FileGenerator();
        fg.generate(fileName);
        ClientMain client = new ClientMain();
        client.sendFile();
    }
}
