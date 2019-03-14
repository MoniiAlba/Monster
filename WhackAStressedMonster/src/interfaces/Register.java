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
    
    public User register(String nickname)  throws RemoteException;
    
    public void printPlayers() throws RemoteException;
    
    public void changeActive(User player, boolean status) throws RemoteException;
    
    public String getMGroup() throws RemoteException;
    
    public int getMSocket() throws RemoteException;
    
    public int getTSocket() throws RemoteException;
    
    public int getTotalPlayers() throws RemoteException;
    
    public User getPlayer(int id) throws RemoteException;
}
