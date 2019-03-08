/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import interfaces.Register;
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
            System.out.println(data[0]);
            
            server.printPlayers();
            
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
