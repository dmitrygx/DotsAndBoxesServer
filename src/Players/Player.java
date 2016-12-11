package Players;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Player {

    private Socket ownSocket = null;
    private Socket enemySocket = null;

    private boolean isGameStarted = false;

    public static int COLOR_RED = 0;
    public static int COLOR_BLUE = 1;

    private int color = COLOR_RED; // 0 - RED, 1 - BLUE

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    private String name = null;

    public Player getEnemyPlayer() {
        return enemyPlayer;
    }

    public void setEnemyPlayer(Player enemyPlayer) {
        this.enemyPlayer = enemyPlayer;
    }

    private StateEventMatrix matrix;
    private Player enemyPlayer = null;

    public StateEventMatrix getMatrix() {
        return matrix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println(name);
        this.name = name;
    }

    public boolean isGameStarted() {
        return isGameStarted;

    }

    public void setGameStarted(boolean gameStarted) {
        isGameStarted = gameStarted;
    }

    public Player(Socket ownSocket, Socket enemySocket) {
        this.ownSocket = ownSocket;
        this.enemySocket = enemySocket;
        matrix = new StateEventMatrix(this);
    }

    public void handleEvent(String buffer) {
        matrix.handleEvent(buffer);
    }

    public void sendMailToItself(String msg) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(ownSocket.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        writer.println(msg);

        System.out.println("SEND to Itself - " + msg);
    }

    public void sendMailToEnemy(String msg) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(enemySocket.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        writer.println(msg);

        System.out.println("SEND to Enemy - " + msg);
    }

    public Socket getOwnSocket() {
        return ownSocket;
    }

    public void setOwnSocket(Socket ownSocket) {
        this.ownSocket = ownSocket;
    }

    public Socket getEnemySocket() {
        return enemySocket;
    }

    public void setEnemySocket(Socket enemySocket) {
        this.enemySocket = enemySocket;
    }
}
