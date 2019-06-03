package computer.networks;


import java.io.*;
import java.net.Socket;

// Лаба 4
// Простой многопоточный сервер для получения файлов от клиентов.

public class Worker implements Runnable {

    protected Socket clientSocket = null;

    private final String DOWNLOADS_PATH = "downloads";

    public Worker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    //получает файл не более 500 мегабайт и записывает его в папку downloads
    @Override
    public void run() {
        try {
            int size = 524288000;       //500 мегабайт размер буфера
            String filename = "unnamed";
            long fileSize = 0L;
            byte[] buffer = new byte[size];
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            byte[] ourByte = new byte[1];

            while(true) {
                fileSize = input.readLong();
                filename = input.readUTF();
                int readBytesCount = input.read(buffer, 0, (int)fileSize);
                if(readBytesCount == -1) break;
                ourByte = new byte[readBytesCount];
                for(int i = 0; i < readBytesCount; i++){
                    ourByte[i] = buffer[i];
                }
                System.out.println("Receive file size... " + readBytesCount);
            }
            input.close();

            makeDownloadsDirectory();

            File file = new File(DOWNLOADS_PATH + File.separatorChar + filename);
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(ourByte);

            bos.close();
            System.out.println("Successful receive file " + filename + " from client.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeDownloadsDirectory() {
        File dir = new File(DOWNLOADS_PATH);
        if(!dir.exists())
            dir.mkdir();
    }

    private void answerSuccessToClient() throws IOException {
        PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
        toClient.println("Thank you for connecting to " + clientSocket.getLocalSocketAddress() + " Goodbye!");
    }
}
