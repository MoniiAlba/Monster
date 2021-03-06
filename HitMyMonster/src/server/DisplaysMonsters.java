/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author soeur
 */
public class DisplaysMonsters implements Runnable{

    private int totalRounds = 0;
    private String multicastGroup = "";
    private int socket = 0;
    public DisplaysMonsters(int rounds, String multicast, int socket) {
        this.totalRounds = rounds;
        this.multicastGroup = multicast;
        this.socket = socket;
    }
    

    @Override
    public void run() {
        MulticastSocket s = null;
        try {

            InetAddress group = InetAddress.getByName(multicastGroup); // destination multicast group 
            s = new MulticastSocket(socket);
            s.joinGroup(group);
            Thread.sleep(2000);
            System.out.println("Starting sending");
            for(int i = 0; i < totalRounds; i++){
                String myMessage = randomMonster();
                System.out.println("Sent: "+myMessage);
                byte[] m = myMessage.getBytes();
                DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 7777);
                s.send(messageOut);
                Thread.sleep(6000);
            }
            

            s.leaveGroup(group);
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } catch (InterruptedException ex) {
            Logger.getLogger(DisplaysMonsters.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }
    
    public String randomMonster(){
        return new Random().nextInt(10) + "";
    }
    
}
