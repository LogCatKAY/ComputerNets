package computer.networks;

import java.util.Scanner;

public class Main {

    public static final int PORT_WORK = 9000;

    public static void main(String[] args) {
        MultiThreadedServer server = new MultiThreadedServer(PORT_WORK);
        new Thread(server).start();

        Scanner sc = new Scanner(System.in);
        System.out.println("Input -1 and press Enter / Return to stop server...");
        while (sc.nextInt() != -1) {
            System.out.println("Input -1 and press Enter / Return to stop server...");
        }

        System.out.println("Stopping server...");
        server.stop();
    }
}
