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
public class RegisterServer implements Register{
    private static int contUsers = 0;
    private User[] players = new User [20];
    private static int totalRounds = 5;
        
    @Override
    public String[] register(String nickname)  throws RemoteException {
        String [] result = new String[5];  //regresamos ip, multicast socket, puerto tcp e id
        boolean userFound = false;
        int i = 0;
        while(!userFound && i < contUsers){
            if(players[i].getNickname().equals(nickname)){
                userFound = true;
                result[3] = ""+ players[i].getId();
                System.out.println("User " + players[i].getNickname() + " already exists, id: " + players[i].getId());
            }
            i++;
        }
        
        if(!userFound){
            User newPlayer = new User(100+contUsers, nickname, 0);
            players[contUsers] = newPlayer;
            result[3] = ""+contUsers;
            System.out.println("User " + players[contUsers].getNickname() + " registered with id: " + players[contUsers].getId());
            contUsers++;
            
        }
        
        result[0] = "228.15.26.37";  //direccion de grupo multicast
        result[1] = "7777";         //socket multicast
        result[2] = "7896";         //puerto tcp
        result[4] = ""+totalRounds;
        return result;
    }
    
    
    
    @Override
    public void printPlayers() throws RemoteException {
        System.out.println("============== Active Players ==============");
        for(int i = 0; i < contUsers; i++){
            System.out.println("Player: " + players[i].getNickname() + " Id: " + players[i].getId() + " Score: " + players[i].getScore());
        }
    }
    
    
    public static void main(String[] args) {
        try{
            System.setProperty("java.security.policy","file:/C:\\Users\\soeur\\Documents\\NetBeansProjects\\HitMyMonster\\src\\server\\server.policy");
            if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            } 
            LocateRegistry.createRegistry(1099);
            
            String name = "Register";
            RegisterServer engine = new RegisterServer();
            Register stub = (Register) UnicastRemoteObject.exportObject(engine, 0);
            
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub); 
            System.out.println("Ready to register players");
            
            Thread sendMonsters = new Thread(new DisplaysMonsters(totalRounds));
            boolean playerFlag = true;
            int i = 0;
            while(playerFlag){
                System.out.println("counter: "+contUsers);
                if(contUsers != 0){
                    System.out.println("SALTEEEE");
                    playerFlag = false;
                }
                i++;
            }
            System.out.println("I will send!");
            sendMonsters.start();
        }catch(RemoteException ex){
            Logger.getLogger(RegisterServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 

    
}
