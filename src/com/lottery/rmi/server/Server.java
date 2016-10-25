package com.lottery.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Iterator;

import com.ahmed.rmi.client.ClientInterface;

public class Server extends UnicastRemoteObject implements ServerInterface {

	private static final long serialVersionUID = 1L;
	public volatile ArrayList<ClientInterface> clients = null;
	public volatile ArrayList<Block> blockChainLedger = null;
	private String prevHash = null;
	
	protected Server() throws RemoteException, NoSuchAlgorithmException {

		clients = new ArrayList<ClientInterface>();
		blockChainLedger = new ArrayList<Block>();
		prevHash = sha256("SHA-256");
	}

	@Override
	public synchronized void registerClient(ClientInterface client) throws RemoteException {
		// TODO Auto-generated method stub
		this.clients.add(client);
		
	}

	

	public synchronized void unregisterChatClient(ClientInterface client) 
			throws RemoteException {
		this.clients.remove(this.clients.indexOf(client));
	
	}
	
	@Override
	public synchronized void sendMessage(String message) throws RemoteException {
		int i = 0;
		while(i<clients.size())
		{
			clients.get(i).receiveMessage(message);
			i++;
		}
		
	}


	@Override
	public Vector<String> getClientNames() throws RemoteException {
		// TODO Auto-generated method stub
		//System.out.println("The Users registered in Lottery server are: ");
		// Iterator itr = (Iterator) clients.iterator();
		
		Vector<String> v = new Vector<String>();
		int i =0;
		while(i<clients.size())
		{
			v.add(clients.get(i).getName());
		//	System.out.println(clients.get(i).getName());
			i++;
		}
		return v;   
	}
	

	@Override
	public Vector<String> getClientHashNumbers() throws RemoteException {
		
		Vector<String> v = new Vector<String>();
		int i =0;
		while(i<clients.size())
		{
			v.add(clients.get(i).getHashNumber());
			i++;
		}
		return v;
	}

	@Override
	public Vector<String> getClientNumbers() throws RemoteException {
		
		Vector<String> v = new Vector<String>();
		int i =0;
		while(i<clients.size())
		{
			v.add(clients.get(i).getNumber());
		//	System.out.println(clients.get(i).getName());
			i++;
		}
		
		return v;
	}
	
	@Override
	public Vector<String> getClientNonces() throws RemoteException {

		Vector<String> v = new Vector<String>();
		int i =0;
		while(i<clients.size())
		{
			v.add(clients.get(i).getNonce());
		//	System.out.println(clients.get(i).getName());
			i++;
		}
		
		return v;
	}


	@Override
	public String getClientLedgers() throws RemoteException {

		String s = "============================\n";
		int i = 0;
		while(i < this.blockChainLedger.size()){
			s +="Transaction : " + (i+1) + "\n";
			s+="Username - " + this.blockChainLedger.get(i).getUsername() + "\n";
			s+="TimeStamp - " + this.blockChainLedger.get(i).getTimeStamp()+ "\n";
			s+="Hash Number - " + this.blockChainLedger.get(i).getHashNumber()+ "\n";
			s+="Nonce - " + this.blockChainLedger.get(i).getNonce()+ "\n";
			s+="Previous Hash - " + this.blockChainLedger.get(i).getPrevHash()+ "\n";
			s += "============================\n";
			i++;
		}
		return s;
	}

	@Override
	public void setClientLedger(long ts, String name, String hashNUmber, String nonce) throws RemoteException, NoSuchAlgorithmException{
	
		Block b = new Block();
		b.setTimeStamp(ts);
		b.setUsername(name);
		b.setHashNumber(hashNUmber);
		b.setPrevHash(this.prevHash);
		b.setNonce(nonce);
		
		// compute hash of all these 4 values and perform hash again, store in prevHash Attribute of server.
		
		this.prevHash = sha256(name + hashNUmber + String.valueOf(ts) + this.prevHash);
		this.blockChainLedger.add(b);
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
