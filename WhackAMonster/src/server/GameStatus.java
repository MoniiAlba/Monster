/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import interfaces.User;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author soeur
 */
public class GameStatus implements Serializable{
    private ArrayList<User> players;
    private int round;
    private final int MAX_HITS = 5;  //5 hits para ganar

    public GameStatus() {
        players = new ArrayList();
        round = 0;
    }
    
    public int getRound(){
        return round;
    }
    
    public ArrayList<User> getPlayers(){
        return players;
    }
    
    public int getTotalPlayers(){
        return players.size();
    }
    
    public User getPlayerByName(String nickname){
        for(User player : players){
            if(player.getNickname().equals(nickname)){
                return player;
            }
        }
        return null;
    }
    
    public User getPlayerById(int id){
        return this.players.get(id);
    }
    
    public User addPlayer(String nickname){
        User p = new User(getTotalPlayers(), nickname, 0);
        players.add(p);
        return p;
    }
    
    
}
