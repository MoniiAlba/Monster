/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import interfaces.Register;
import interfaces.User;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author soeur
 */
public class Stresser {
    private Register server;
    private String name;
    final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";
    private static String multicastGroup = "228.15.26.37";
    private static int multicastSocket = 7777;
    private static int tcpSocket = 8896;

    public Stresser() throws RemoteException {
        server = connectRMI();
    }

    public Register getServer() {
        return server;
    }

    public void setServer(Register server) {
        this.server = server;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTcpSocket() {
        return tcpSocket;
    }

    public String getMulticastGroup() {
        return multicastGroup;
    }

    public int getMulticastSocket() {
        return multicastSocket;
    }
    
    
    
    private Register connectRMI(){
        try {
            String path = System.getProperty("user.dir") + "/src/client/player.policy";
            System.setProperty("java.security.policy","file:"+path);
            System.setProperty("sun.net.maxDatagramSockets","601");
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }
            
            String name = "Register";
            Registry registry = LocateRegistry.getRegistry("localhost"); // server's ip address
            //Registry registry = LocateRegistry.getRegistry("ip"); // server's ip address
            Register server = (Register) registry.lookup(name);
            return server;
        }catch (RemoteException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private String nameGenerator() throws RemoteException{
        float oldPlayer = new Random().nextFloat();
        if(oldPlayer >= 0.8){
            try {
                if(this.server.getTotalPlayers() != 0)
                    return this.server.getPlayer(0).getNickname();
            } catch (RemoteException ex) {
                Logger.getLogger(Stresser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        StringBuilder builder = new StringBuilder();
        while (builder.toString().length() == 0) {
            int length = new Random().nextInt(9);
            for (int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(new Random().nextInt(36)));
            }
        }
        return builder.toString();
    }
    
    public static void main(String[] args) throws RemoteException {
        

        for(int i = 0; i < 220; i++){

            Stresser stress = new Stresser();
            stress.setName(stress.nameGenerator());
            User player = stress.getServer().register(stress.getName());

            System.out.println(player.getNickname());
            if (player != null && !player.getActive()) {
                stress.getServer().changeActive(player, true);
                System.out.println("Jugador activo");

                MonsterListener monsterListener = new MonsterListener(stress.getMulticastGroup(), stress.getMulticastSocket(), player, stress.getTcpSocket());
                Thread catchMonsters = new Thread(monsterListener);
                catchMonsters.start();

            } else {
                System.out.println("Usuario ya estaba activo");
            }
        }
        
        

    }
    
}
