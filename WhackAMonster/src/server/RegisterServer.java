/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import interfaces.User;
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
    
    public String getMGroup(){
        return multicastGroup;
    }
    
    public int getMSocket(){
        return multicastSocket;
    }
    
    public int getTSocket(){
        return tcpPort;
    }
    
    @Override
    public User register(String nickname) throws RemoteException {
        User player = this.board.getPlayerByName(nickname);
        
        if(player != null){  //ya existía
            System.out.println("User " + nickname + " already exists, id: " + player.getId());
        }else{
            player = this.board.addPlayer(nickname);
            System.out.println("User " + nickname + " registered with id: " + player.getId());
        }
        return player;
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

    @Override
    public void setActive(User player) throws RemoteException {
        if(player != null){
            this.board.getPlayers().get(player.getId()).setActive(true);
        }
    }
    
}
