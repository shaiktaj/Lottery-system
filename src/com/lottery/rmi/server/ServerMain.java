package com.lottery.rmi.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ServerMain {
	public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException, NoSuchAlgorithmException {
		
		Server lotteryServer = new Server();
		Naming.rebind("lotteryServer", lotteryServer);
		
		while(true){
			
			lotteryServer.clients.clear();		
		
		ArrayList<Block> blockChainLedger = new ArrayList<Block>();
		String result = null;
		
		Vector<String> UsersList = new Vector<String>();
		Vector<String> WinnersList = new Vector<String>();
		Vector<String> HashNumbersList = new Vector<String>();
		Vector<String> NumbersList = new Vector<String>();
		
		
		
		System.out.println("\n\nWaiting for (1 min) for users to join the Lottery..");

		while(lotteryServer.getClientHashNumbers().size() == 0){
			try {
			    Thread.sleep(60000);                 //1000 milliseconds is one second.
			    System.out.println("\nStill waiting for users to Register with Lottery Server...");
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
		
		if(lotteryServer.getClientHashNumbers().size() > 0){
			System.out.println("Waiting Done..... \n\nUsers in the lottery are: \nUser - HashNumber");
			
			UsersList = lotteryServer.getClientNames();
			HashNumbersList = lotteryServer.getClientHashNumbers();
			int x =0;
			while(x< lotteryServer.getClientHashNumbers().size())
			{
				
			System.out.println(lotteryServer.getClientNames().get(x) + " - " + lotteryServer.getClientHashNumbers().get(x));	
				
				x++;
			}
		
		
	System.out.println("\n\n");
	
	
	while(lotteryServer.getClientNumbers().get(lotteryServer.getClientNames().size() -1) == null)
		{		
		System.out.println("waiting for all users Numbers...");
		try {
		    Thread.sleep(15000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}

	
	if(lotteryServer.getClientNumbers().size() == lotteryServer.getClientNames().size()){
		System.out.println("Waiting Done..... \n\nUser Numbers received are: \nUser - Number");
		
		NumbersList = lotteryServer.getClientNumbers();
		int i =0;
		while(i< lotteryServer.getClientNumbers().size())
		{
			
		System.out.println(lotteryServer.getClientNames().get(i) + " - " + lotteryServer.getClientNumbers().get(i));

			
			i++;
		}
	   }
			
	System.out.println("\n\nComputing User Number Hashes..");
	
		int i =0;
		while(i< lotteryServer.getClientNames().size()){
			String s = lotteryServer.getClientNames().get(i);
			String hashValue = lotteryServer.getClientNumbers().get(i) + lotteryServer.getClientNonces().get(i) + s;
			
				if(sha256(hashValue).equals(lotteryServer.getClientHashNumbers().get(i)))
				{
					lotteryServer.sendMessage("User " + s + " is genuine..." );
					System.out.println("User " + s + " is genuine..." );
				}
				else{
					System.out.println("User " + s + " is not genuine...Removing from Lottery.." );
					lotteryServer.sendMessage("User " + s + " is not genuine...Removing from Lottery..");
					HashNumbersList.remove(UsersList.indexOf(s));
					NumbersList.remove(UsersList.indexOf(s));
					UsersList.remove(s);
				}
			i++;
		}
		
		if(UsersList.size() < lotteryServer.getClientNames().size()){
			System.out.println("\nRemaining Users in the Lottery..");
			int j =0;
			while(j < UsersList.size())
			{
				System.out.println(UsersList.get(j));
				j++;
			}
		}
		int sum;
		System.out.println("\nComputing Lottery results... ");
		
		switch(HashNumbersList.size()){
		
		case 0: 
			System.out.println("No user is genuine.. Lottery System ending..");
			result = "No user is genuine.. Lottery System ending..";
			break;
			
		case 1:
			System.out.println("Only one user left: " + UsersList.get(0) + ". So he/she is the winner!" );
			result = "Only one user left: " + UsersList.get(0) + ". So he/she is the winner!" ;
			break;
			
		case 2:
			sum = ((Integer.parseInt(NumbersList.get(0)) + Integer.parseInt(NumbersList.get(1))) % 2);
			
			if(((Integer.parseInt(NumbersList.get(0)) % 2) == sum) && ((Integer.parseInt(NumbersList.get(1)) % 2) == sum))
			{		
				System.out.println("There is a tie between: " + UsersList.get(0) + " and " +  UsersList.get(1));
				result = "There is a tie between: " + UsersList.get(0) + " and " +  UsersList.get(1);
			
			}else if(((Integer.parseInt(NumbersList.get(0)) % 2) != sum) && ((Integer.parseInt(NumbersList.get(1)) % 2) != sum)){
				System.out.println("There is no winner..Lottery ending.. ");
				result = "There is no winner..Lottery ending.. ";
			}
			else{
				if((Integer.parseInt(NumbersList.get(0))) == sum){
					System.out.println("The winner is: " + UsersList.get(0));
					result = "The winner is: " + UsersList.get(0);
				}
				else {  //if((Integer.parseInt(NumbersList.get(1))) == hashSum)
					System.out.println("The winner is: " + UsersList.get(1));
					result = "The winner is: " + UsersList.get(1);
				}
			}
			
			break;
			
		case 3:
			sum = ((Integer.parseInt(NumbersList.get(0)) + Integer.parseInt(NumbersList.get(1)) + Integer.parseInt(NumbersList.get(2))) % 3);
			// System.out.println("Hash sum is : " + );
			 int k =0;
			 while(k < 3)
			 {
				 if(((Integer.parseInt(NumbersList.get(k)) % 3) == sum))
					 WinnersList.add(UsersList.get(k));
				 k++;
			 }	
			 
			 if(WinnersList.size() == 0){
				 result = "There is no winner..Lottery ending.. ";
				 System.out.println("There is no winner..Lottery ending.. ");
			 }
			 
			break;
			
			default:
				break;
		
		}
	
		
		if(WinnersList.size() != 0){
			result = "Winners: ";
			System.out.println("Winners: ");
		    
			int l=0;
			while(l< WinnersList.size()){
				result += WinnersList.get(l) + " ";
				System.out.println(WinnersList.get(l));
				l++;
			}
		}
		
		lotteryServer.sendMessage(result);
		 System.out.println("Lottery Server ending.. ");	
	
		}
		else{
			 System.out.println("No user registered in 2 minutes. Lottery Server ending.. ");	
		}
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
