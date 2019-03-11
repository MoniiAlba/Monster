/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import interfaces.Register;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author soeur
 */
public class Player {
   

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("WELCOME!");
        System.out.println("Enter your nickname: ");
        String s = reader.next();
        reader.close();
        System.out.println("==================================");
        System.out.println(s);
        
        try {
            System.setProperty("java.security.policy","file:/C:\\Users\\soeur\\Documents\\NetBeansProjects\\HitMyMonster\\src\\client\\player.policy");
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }
            
            String name = "Register";
            Registry registry = LocateRegistry.getRegistry("localhost"); // server's ip address
            Register server = (Register) registry.lookup(name);
            
            String [] data = server.register(s);
            System.out.println("Multicast group: "+data[0]);
            System.out.println("Multicast socket: "+data[1]);
            System.out.println("TCP port: "+data[2]);
            System.out.println("My ID: "+data[3]);
            System.out.println("Total of rounds: "+data[4]);
            server.printPlayers();
            
            listenToMonster(data[0],Integer.parseInt(data[1]), Integer.parseInt(data[4]));
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void listenToMonster(String multicastG, int socket, int totRounds){
        MulticastSocket s =null;
   	 try {
                InetAddress group = InetAddress.getByName(multicastG); // destination multicast group 
	    	s = new MulticastSocket(socket);
	   	s.joinGroup(group); 

	    	byte[] buffer = new byte[1000];
 	   	for(int i=0; i< totRounds; i++) {
                    System.out.println("Waiting for messages");
                    DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
 		    s.receive(messageIn);
 		    System.out.println("Hit Monster: " + (new String(messageIn.getData())).trim());
  	     	}
	    	s.leaveGroup(group);		
 	    }
         catch (SocketException e){
             System.out.println("Socket: " + e.getMessage());
	 }
         catch (IOException e){
             System.out.println("IO: " + e.getMessage());
         }
	 finally {
            if(s != null) s.close();
        }
    }
}
