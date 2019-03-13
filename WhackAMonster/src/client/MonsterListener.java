/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

/**
 *
 * @author soeur
 */
public class MonsterListener implements Runnable{
    private boolean gameEnded;
    private String multicastGroup;
    private int multicastSocket;
    BoardGUI window;

    public MonsterListener(String multicastGroup, int multicastSocket, BoardGUI window) {
        this.gameEnded = false;
        this.multicastGroup = multicastGroup;
        this.multicastSocket = multicastSocket;
        this.window = window;
    }
    
    
    
    public void run(){
        MulticastSocket s = null;
        try {
            InetAddress group = InetAddress.getByName(multicastGroup); // destination multicast group 
            s = new MulticastSocket(multicastSocket);
            s.joinGroup(group);

            byte[] buffer = new byte[1000];
            while(!gameEnded) {
                System.out.println("Waiting for messages");
                DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                s.receive(messageIn);
                String message = (new String(messageIn.getData())).trim();
                int monster = Integer.parseInt(message.split(",")[0]);
                int round = Integer.parseInt(message.split(",")[1]);
                System.out.println("Hit Monster: " + monster + ", round: " + round);
                window.changeColorButton(monster, round);
            }
            s.leaveGroup(group);
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (s != null) {
                s.close();
            }
        }
    
    }
}
