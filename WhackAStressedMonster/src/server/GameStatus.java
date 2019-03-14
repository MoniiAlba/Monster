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
    private User[] scores;
    private User winner;
    private int actualRound;
    private final int MAX_HITS = 5;  //5 hits para ganar

    public GameStatus() {
        players = new ArrayList();
        scores = new User[100];
        actualRound = 0;
        winner = null;
    }
    
    public int getRound(){
        return actualRound;
    }
    
    public User getWinner(){
        return winner;
    }
    
    public void resetWinner(){
        winner = null;
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
    
    public void incrementScore(int id, int round){
        if(scores[round] == null){ //revisa que ningun usuario haya dado ya este round
            User p = players.get(id);
            int score = p.getScore() + 5;
            System.out.println("New score for " + p.getNickname() + ": " + score);
            p.setScore(score);
            scores[round] = p;
            if(score == 5*MAX_HITS){
                winner = p;
                p.setScore(0);
            }                        
        }
        
    }
    
    
}
