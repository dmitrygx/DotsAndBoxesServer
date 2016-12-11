package MultiThreadedServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;

import Players.*;
import org.json.simple.JSONObject;

import static Players.Player.COLOR_BLUE;
import static Players.Player.COLOR_RED;

public class MultiThreadedServer implements Runnable{

    protected int          serverPort    = 8080;
    protected ServerSocket serverSocket  = null;
    protected boolean      isStopped     = false;
    protected Thread       runningThread = null;

    protected LinkedHashSet<Player> playersSet;
    protected LinkedList<Player> pendingPlayers;

    public MultiThreadedServer(int port){
        this.serverPort = port;
        playersSet = new LinkedHashSet<>(2);
        pendingPlayers = new LinkedList<>();
    }

    public void run(){
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();

        while(!isStopped()){
            Socket clientSocket = null;

            Player newPlayer;

            try {
                clientSocket = this.serverSocket.accept();

                System.out.println("NEW client");
                if (pendingPlayers.isEmpty()) {
                    newPlayer = new Player(clientSocket, null);
                    newPlayer.setGameStarted(false);
                    pendingPlayers.add(newPlayer);
                }
                else {
                    Player enemyPlayer = pendingPlayers.pollFirst();
                    enemyPlayer.setEnemySocket(clientSocket);
                    enemyPlayer.setGameStarted(true);
                    enemyPlayer.getMatrix().setCurrentState(StateEventMatrix.ACTION);
                    enemyPlayer.setColor(COLOR_RED);
                    playersSet.add(enemyPlayer);

                    newPlayer = new Player(clientSocket, clientSocket);
                    newPlayer.setEnemySocket(enemyPlayer.getOwnSocket());
                    newPlayer.setGameStarted(true);
                    newPlayer.setEnemyPlayer(enemyPlayer);
                    newPlayer.setColor(COLOR_BLUE);
                    playersSet.add(newPlayer);

                    enemyPlayer.setEnemyPlayer(newPlayer);
                }

            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException(
                        "Error accepting client connection", e);
            }
            new Thread(new WorkerRunnable(newPlayer)).start();
        }
        System.out.println("Server Stopped.") ;
    }


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + serverPort, e);
        }
    }

}
