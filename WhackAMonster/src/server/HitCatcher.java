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
    
    private int tcpPort;

    public HitCatcher(int tcpPort) {
        this.tcpPort = tcpPort;
    }
    
    

    @Override
    public void run() {
        Socket client = null;
        try {
            int serverPort = this.tcpPort;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            System.out.println("================ Listening ==================");
            while (true) {
                System.out.println("Waiting for hits...");
                Socket clientSocket = listenSocket.accept();  // Listens for a connection to be made to this socket and accepts it. The method blocks until a connection is made. 
                //Connection c = new Connection(clientSocket);
                //c.run();
                
                DataInputStream in;
                DataOutputStream out;
                client = clientSocket;
                
                in = new DataInputStream(client.getInputStream());
                out = new DataOutputStream(client.getOutputStream());
                
                String data = in.readUTF();
                System.out.println("Message received from: " + clientSocket.getRemoteSocketAddress() + " is " + data);
            }
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

