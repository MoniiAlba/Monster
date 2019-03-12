/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import interfaces.Register;
import java.rmi.RemoteException;

/**
 *
 * @author soeur
 */
public class RegisterServer implements Register{
    GameStatus board;
    private String multicastGroup;
    private int multicastSocket;
    private int tcpPort;

    public RegisterServer(GameStatus board, String mGroup, int mSocket, int tcp) {
        super();
        this.board = board;
        this.multicastGroup = mGroup;
        this.multicastSocket = mSocket;
        this.tcpPort = tcp;
    }
    
    public GameStatus getBoard() throws RemoteException{
        return this.board;
    }
    
    @Override
    public String[] register(String nickname) throws RemoteException {
        String [] result = new String[5];  //regresamos ip, multicast socket, puerto tcp e id
        User player = this.board.getPlayerByName(nickname);
        result[0] = this.multicastGroup;  //direccion de grupo multicast
        result[1] = this.multicastSocket + "";         //socket multicast
        result[2] = this.tcpPort + "";
        
        if(player != null){  //ya existía
            System.out.println("User " + nickname + " already exists, id: " + player.getId());
            result[3] = player.getId() + "";
            result[4] = "exists";
        }else{
            int newId = this.board.addPlayer(nickname);
            System.out.println("User " + nickname + " registered with id: " + newId);
            result[4] = "new";
        }
        
        return result;
    }
    
    @Override
    public void printPlayers() throws RemoteException {
        System.out.println("============== Active Players ==============");
        int contUsers = this.board.getTotalPlayers();
        System.out.println("Total de jugadores: " + contUsers);
        for(User player : this.board.getPlayers()){
            System.out.println("Player " + player.getNickname() + " with id " + player.getId() + "has score: " + player.getScore());
        }
    }
    
}
