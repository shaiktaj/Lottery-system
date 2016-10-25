package com.lottery.rmi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;
import java.util.Iterator;


import com.ahmed.rmi.server.ServerInterface;


public class ClientMain {

public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException{
	
	String rmiURL = "rmi://localhost/lotteryServer";
	ServerInterface server = (ServerInterface) Naming.lookup(rmiURL);
		
		int Option = 0;
		Scanner scanner = new Scanner(System.in);
		boolean setClient = false;
	       Client client = null;
	     
	       Vector<String>  clientsList = new Vector<String>();
	    
		try{
			while(Option !=4)
			{
			       System.out.println("\nOptions Menu: Enter "); 
			       System.out.println("  0 - To List all Users in the Lottery"); 
			       System.out.println("  1 - To Join the Lottery"); 
			       System.out.println("  2 - To send your Number and nonce to the Lottery Server"); 
			       System.out.println("  3 - To Request Ledger from Server");
			       System.out.println("  4 - To Quit"); 
			       System.out.println("/n/n please Enter an Option: ");
			       Option = scanner.nextInt();
			       	       
			       switch(Option){
			       
			       case 0:
			    	   
			    	   // to List all Users (CLients) in the Lottery
			    	   
			    	 int q =0;
			    	 if(server.getClientNames().size() == 0){
			    		 System.out.println("No user registered yet! ");
			    	 }
			    	 while(q < server.getClientNames().size())
			    	 {
			    		 System.out.println("Users currently registered: ");
			             System.out.println(server.getClientNames().get(q));
			             q++;
			    	 }
			    	 break;
			    	        // To Join Lottery
			       case 1:
			   		
				    			System.out.println("Please enter the Client name: ");
					    	   	String clientName = scanner.next();
					    	   	
					    	   	System.out.println("Please enter the Client Number (gets hashed and sent to Server): ");
					    	   	String clientNumber = scanner.next();
					    	   	
					    	   	System.out.println("Please enter the Client Nonce (gets hashed and sent to Server): ");
					    	   	String clientNonce = scanner.next();
					    	   	
					    	   	
					    	   	 clientNumber = sha256(clientNumber + clientNonce + clientName);
					    	   	// Implement hash here
					    	   	
					    	//   	System.out.println(System.nanoTime());
					    	   	System.out.println("Connecting to Lottery Server "+ "....");
					    	   	client = new Client(clientName, clientNumber,server);
					    	  
					    	   	// For use while messaging
					    	   	setClient = true;
					    	   	
					    	   	// Adding this Client to server's Client List
					    	   	
					    	   	if(clientsList.contains(clientName))
					    	   	{
					    	   		System.out.println("Unable to join! Client with Name " + clientName + " already joined the Lottery! ");
					    	   	}
					    	   	else{
					    	   	 	clientsList.add(clientName);
					    	   	}				    	   	 	
				    		    System.out.println(clientName + " joined the Lottery..\n\n");  
				    		    client.server.sendMessage("\n" +clientName + "'s Hashed Number is : " + clientNumber + "\n\n");
				    		    long millis = System.currentTimeMillis();
				    		    server.setClientLedger(millis, clientName, clientNumber, clientNonce);
				    		 //   System.out.println();
			    	   break; 
			   
			       case 2:
				    	   if(setClient){	
				    		   client.executing();
				    		   	   }
				    	   else{
				    		   System.out.println("You have not joined the Lottery yet!");
				    	   }
				    	   
				    	   break;
			    	   
			       case 3:
			    	   System.out.println(server.getClientLedgers());
			    	   break;
			    	   
			    	   default:
			    	   break;
			       }
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
  
    }

	static String sha256(String input) throws NoSuchAlgorithmException {
	    
		MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
	    byte[] result = mDigest.digest(input.getBytes());
	    
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < result.length; i++) {
	        sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
	    }
	     
	    return sb.toString();
}

}
		
