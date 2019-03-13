/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author soeur
 */
public class HitCatcher implements Runnable{
    private GameStatus board;
    private int tcpPort;
    private boolean gameEnded;

    public HitCatcher(int tcpPort, GameStatus b) {
        this.tcpPort = tcpPort;
        this.board = b;
        this.gameEnded = false;
        
    }
    
    

    @Override
    public void run() {
        Socket client = null;
        try {
            int serverPort = this.tcpPort;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            System.out.println("================ Listening ==================");
            Socket clientSocket = null;
            while (!gameEnded) {
                System.out.println("Waiting for hits...");
                clientSocket = listenSocket.accept();  // Listens for a connection to be made to this socket and accepts it. The method blocks until a connection is made. 
                                
                DataInputStream in;
                client = clientSocket;
                
                in = new DataInputStream(client.getInputStream());
                
                String data = in.readUTF();
                String name = data.split(",")[0];
                int id = Integer.parseInt(data.split(",")[1]);
                int round = Integer.parseInt(data.split(",")[2]);
                System.out.println("Hit received from user " + name + " from round " + round);
                board.incrementScore(id, round);
            }
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Listen :" + e.getMessage());
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
    
}

