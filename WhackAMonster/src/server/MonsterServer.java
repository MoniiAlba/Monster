/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import interfaces.Register;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author soeur
 */
public class MonsterServer {
    private static GameStatus board;
    private static int totalRounds = 5;
    private static String multicastGroup = "228.5.6.10";
    private static int multicastSocket = 7777;
    private static int tcpSocket = 8896;
    
    
    public static void main(String[] args) {
        board = new GameStatus();
        
        try{
            String path = System.getProperty("user.dir") + "/src/server/server.policy";
            System.setProperty("java.security.policy","file:"+path);
            if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            } 
            
            LocateRegistry.createRegistry(1099);
            
            String name = "Register";
            RegisterServer loginRMI = new RegisterServer(board, multicastGroup, multicastSocket, tcpSocket);
            Register stub = (Register) UnicastRemoteObject.exportObject(loginRMI, 0);
            
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub); 
            System.out.println("Ready to register players");
            
            Thread sendMonsters = new Thread(new DisplaysMonsters(board,multicastGroup, multicastSocket));
            System.out.println("I will listen!");
            Thread listenHits = new Thread(new HitCatcher(tcpSocket, board));
            listenHits.start();
            System.out.println("I will send!");
            sendMonsters.start();
            
            
            
            
            
        }catch(RemoteException ex){
            Logger.getLogger(RegisterServer.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
}
