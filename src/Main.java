import MultiThreadedServer.*;

public class Main {

    static boolean isStopped = false;

    public static void main(String[] ar) {
        MultiThreadedServer server = new MultiThreadedServer(9007);

        new Thread(server).start();

        while (!isStopped) {
        }

        System.out.println("Stopping Server");
        server.stop();
    }
}
