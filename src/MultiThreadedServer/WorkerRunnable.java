package MultiThreadedServer;

import Players.Player;
import Players.StateEventMatrix;
import org.json.simple.JSONObject;
import sun.net.ConnectionResetException;

import java.io.*;
import java.net.Socket;

public class WorkerRunnable implements Runnable{

    Player player = null;

    InputStream ownInput = null;
    OutputStream ownOutput = null;


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
                    endGame();
                    return;
                }
            }

        } catch (IOException e) {
            //report exception somewhere.
            endGame();
            return;
        }
    }

    private void endGame() {
        JSONObject msgToEnemy = new JSONObject();

        msgToEnemy.put("event", StateEventMatrix.CHANGE_STATE);
        msgToEnemy.put("state", StateEventMatrix.END);
        msgToEnemy.put("result_of_game", 1);
        msgToEnemy.put("winner", player.getEnemyPlayer().getName());
        msgToEnemy.put("loser", player.getName());
        player.sendMailToEnemy(msgToEnemy.toString());

        try {
            player.getEnemySocket().close();
        } catch (IOException e) {
            return;
        }
    }
}
