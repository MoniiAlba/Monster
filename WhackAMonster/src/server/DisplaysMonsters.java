/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import interfaces.User;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author soeur
 */
public class DisplaysMonsters implements Runnable{
    private GameStatus board;
    private int round = 0;
    private String multicastGroup = "";
    private int socket = 0;
    private boolean gameEnded = false;
    
    public DisplaysMonsters(GameStatus board, String multicast, int socket) {
        this.multicastGroup = multicast;
        this.socket = socket;
        this.board = board;
    }
    
    public void setGameEnded(){
        this.gameEnded = true;
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
            while(!gameEnded){
                String myMessage = randomMonster() + "," + this.round;
                System.out.println("Sent: "+myMessage);
                byte[] m = myMessage.getBytes();
                DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 7777);
                s.send(messageOut);
                this.round++;
                Thread.sleep(3000);
                if(this.board.getWinner()!=null){
                    gameEnded = true;
                    ArrayList<User> finalPlayers = board.getPlayers();
                    System.out.println("=========== GAME ENDED ===========");
                    System.out.println("Winner: " + this.board.getWinner().getNickname());
                    myMessage = "Winner " + this.board.getWinner().getNickname();
                    m = myMessage.getBytes();
                    messageOut = new DatagramPacket(m, m.length, group, 7777);
                    s.send(messageOut);
                    for(User p : finalPlayers){
                        System.out.println("Nickname: " + p.getNickname() + ", score: " + p.getScore());
                    }
                }
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
        return new Random().nextInt(9) + "";
    }
    
}
