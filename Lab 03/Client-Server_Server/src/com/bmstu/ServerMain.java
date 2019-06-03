package com.bmstu;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

// Лаба 3
// Очень простой однопоточный сервер для приема файла от клиента.

public class ServerMain {

    private static final String fileName = "file.txt";

    private void printFile(String filePath) {
        File file = new File(filePath);

        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //TODO: можно лучше - у клиента есть способы явно указать имя и длину, а у сервера эту информацию получить.
    // На клиенте поправлено, но закомментировано. Можно реализовать на сервере.
    public void receiveFileFromClient() {
        try {
            ServerSocket serverSocket = new ServerSocket(9000);
            Socket clientSocket;

            while(true) {
                clientSocket = serverSocket.accept();
                int size = 214748364;
                byte[] buffer = new byte[size];
                DataInputStream input = new DataInputStream(clientSocket.getInputStream());
                byte[] ourByte = new byte[1];

                while(true) {
                    int readBytesCount = input.read(buffer);
                    if(readBytesCount == -1) break;
                    ourByte = new byte[readBytesCount];
                    for(int i = 0; i < readBytesCount; i++){
                        ourByte[i] = buffer[i];
                    }
                    System.out.println("Receive file size... " + readBytesCount);
                }
                input.close();

                File file = new File(fileName);
                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                bos.write(ourByte);

                bos.close();

                printFile(fileName);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServerMain server = new ServerMain();
        server.receiveFileFromClient();
    }
}
