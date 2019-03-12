/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import server.GameStatus;

/**
 *
 * @author soeur
 */
public interface Register  extends Remote {
    
    public String [] register(String nickname)  throws RemoteException;
    
    public void printPlayers() throws RemoteException;
    
    public GameStatus getBoard() throws RemoteException;
    
    
}
