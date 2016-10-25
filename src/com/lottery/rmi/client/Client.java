package com.lottery.rmi.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import com.ahmed.rmi.server.ServerInterface;

public class Client extends UnicastRemoteObject implements ClientInterface {

	public String name = null;
	public ServerInterface server;
	public String hashNumber = null;
	public String number = null;
	public String nonce = null;
	
	protected Client(String name, String number, ServerInterface server) throws RemoteException {
		this.name = name;
		this.server = server;
		this.hashNumber = number;
		this.number = null;
		server.registerClient(this);
	}

	@Override
	public void receiveMessage(String message) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println(message);
		
	}

	public void executing(){
		System.out.println("Please enter your Number to send: ");	
		Scanner s = new Scanner(System.in);
		String message;
	
	       	message = s.nextLine();
	       	
	       	System.out.println("Please enter the Nonce: ");	
	       	String n = s.nextLine();
	       	
	    	try{
				server.sendMessage(name + " : " + message);
				this.number = message;
				this.nonce = n;
			}
			catch(RemoteException e){
				e.printStackTrace();
			}
	}

	@Override
	public String getName() throws RemoteException {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String getHashNumber() throws RemoteException {
		// TODO Auto-generated method stub
		return hashNumber;
	}

	@Override
	public String getNumber() throws RemoteException {
		// TODO Auto-generated method stub
		return number;
	}

	@Override
	public String getNonce() throws RemoteException {
		// TODO Auto-generated method stub
		return nonce;
	}

	
	
}
