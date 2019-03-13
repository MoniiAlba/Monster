/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import interfaces.User;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import server.GameStatus;

/**
 *
 * @author soeur
 */
public class MonsterListener implements Runnable{
    private boolean gameEnded;
    private String multicastGroup;
    private int multicastSocket;
    private MulticastSocket mS;
    BoardGUI window;

    public MonsterListener(String multicastGroup, int multicastSocket, BoardGUI window) {
        this.gameEnded = false;
        this.multicastGroup = multicastGroup;
        this.multicastSocket = multicastSocket;
        this.window = window;
    }
    
    public void closeMS(){
        this.mS.close();
    }
    
    public void run(){
        mS = null;
        try {
            InetAddress group = InetAddress.getByName(multicastGroup); // destination multicast group 
            mS = new MulticastSocket(multicastSocket);
            mS.joinGroup(group);

            
            while(!gameEnded) {
                byte[] buffer = new byte[1000];
                System.out.println("Waiting for messages");
                DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                mS.receive(messageIn);
                String message = (new String(messageIn.getData())).trim();
                if(message.contains("Winner")){
                    String name = message.split(",")[1];
                    WinnerGUI ventanita = new WinnerGUI(name);
                    ventanita.setLocationRelativeTo(null);
                    ventanita.setVisible(true);
                }else{
                    int monster = Integer.parseInt(message.split(",")[0]);
                    int round = Integer.parseInt(message.split(",")[1]);
                    System.out.println("Hit Monster: " + monster + ", round: " + round);
                    window.changeColorButton(monster, round);
                }
                                
            }
            
            mS.leaveGroup(group);
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (mS != null) {
                mS.close();
            }
        }
    
    }
}
