/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.io.Serializable;

/**
 *
 * @author soeur
 */
public class User implements Serializable{
    private int id;
    private String nickname;
    private int score;
    private boolean active; 

    public User(int id, String nickname, int score) {
        this.id = id;
        this.nickname = nickname;
        this.score = score;
        this.active = false;
    }

    public int getId() {
        return this.id;
    }

    public String getNickname() {
        return this.nickname;
    }

    public int getScore() {
        return this.score;
    }

    public boolean getActive(){
        return this.active;
    }
    
    public void setActive(boolean status){
        this.active = status;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setScore(int score) {
        this.score = score;
    }

    
    
    
}
