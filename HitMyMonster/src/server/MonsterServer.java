/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author soeur
 */
public class MonsterServer {
    private static int contUsers = 0;
    private User[] players = new User [20];
    private static int totalRounds = 5;
    private static String multicastGroup = "228.15.26.37";
    private static int multicastSocket = 7777;
    
    public static void main(String[] args) {
        
        Thread rmiRegistry = new Thread(new RegisterServer());
        rmiRegistry.start();
        
        
        Thread sendMonsters = new Thread(new DisplaysMonsters(totalRounds, multicastGroup, multicastSocket));
            
        System.out.println("I will listen!");
        Thread listenHits = new Thread(new HitCatcher());
        listenHits.start();
        System.out.println("I will send!");
        sendMonsters.start();
    }
}
