/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import interfaces.User;
import java.awt.Color;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.GameStatus;

/**
 *
 * @author soeur
 */
public class MonsterListener implements Runnable{
    private boolean gameEnded;
    private String multicastGroup;
    private int multicastSocket;
    private int tcpSocket;
    private MulticastSocket mS;
    private User player;

    public MonsterListener(String multicastGroup, int multicastSocket, User player, int tcpSocket) {
        this.gameEnded = false;
        this.multicastGroup = multicastGroup;
        this.multicastSocket = multicastSocket;
        this.player = player;
        this.tcpSocket = tcpSocket;
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
                //System.out.println("Waiting for messages");
                DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                mS.receive(messageIn);                
                String message = (new String(messageIn.getData())).trim();
                String [] received = message.split(",");
                if(message.contains("Winner")){
                    String name = received[1];
                    //System.out.println("===================================");
                    System.out.println("Winner: " + name);
                    //System.out.println("===================================");
                    
                }else{
                    int monster = Integer.parseInt(received[0]);
                    int round = Integer.parseInt(received[1]);
                    //System.out.println("Hit Monster: " + monster + ", round: " + round);
                    
                    Hammer answer = new Hammer(player, tcpSocket);
                    
                    //float correctHit = new Random().nextFloat();
                    //if(correctHit >= 0.3){
                        answer.whack(round, received[2]);
                    //}
                    
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
