package MultiThreadedServer;

import Players.Player;
import sun.net.ConnectionResetException;

import java.io.*;
import java.net.Socket;

public class WorkerRunnable implements Runnable{

    Player player = null;

    InputStream ownInput = null;
    OutputStream ownOutput = null;

    InputStream enemyInput = null;
    OutputStream enemyOutput = null;

    public WorkerRunnable(Player player) {
        this.player = player;
    }

    public void run() {
        try {
            ownInput = player.getOwnSocket().getInputStream();
            ownOutput = player.getOwnSocket().getOutputStream();
            BufferedReader buffread = new BufferedReader(new InputStreamReader(ownInput));

            while (true) {
                String msg;
                try {
                    if ((msg = buffread.readLine()) != null) {
                        player.handleEvent(msg);
                    }
                } catch (ConnectionResetException sockex) {
                    player.getOwnSocket().close();
                }
            }

        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}
