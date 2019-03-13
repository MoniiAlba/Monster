/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import interfaces.User;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author soeur
 */
public class Hammer {
    private User player;
    private int socket;
    
    public Hammer(User player, int socket) {
        this.player = player;
        this.socket = socket;
    }
    
    
    
    public void whack (String str) {

	Socket s = null;
	    try {
	    	int serverPort = this.socket;
	   	
                s = new Socket("localhost", serverPort);    
             //   s = new Socket("127.0.0.1", serverPort);    
		DataInputStream in = new DataInputStream( s.getInputStream());
		DataOutputStream out =
			new DataOutputStream( s.getOutputStream());
		out.writeUTF(str);        	// UTF is a string encoding 
                
                //Ventana ventana = new Ventana();
                //ventana.setVisible(true);
                
                //while (true) {
                 //   System.out.println("");
                //}
                
		//String data = in.readUTF();	      
                //System.out.println("Received: "+ data) ;      
       	    } 
            catch (UnknownHostException e) {
		System.out.println("Sock:"+e.getMessage()); 
	    }
            catch (EOFException e) {
                System.out.println("EOF:"+e.getMessage());
    	    } 
            catch (IOException e) {
                System.out.println("IO:"+e.getMessage());
            } finally {
                if(s!=null) 
                    try {
                        s.close();
                    } catch (IOException e){
                    System.out.println("close:"+e.getMessage());}
                    }
            }
    
}
