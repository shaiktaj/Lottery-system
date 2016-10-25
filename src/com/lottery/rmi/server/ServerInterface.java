package com.lottery.rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Vector;

import com.ahmed.rmi.client.ClientInterface;

public interface ServerInterface extends Remote{
	
	void registerClient(ClientInterface chatClient) throws RemoteException;
	void sendMessage(String message) throws RemoteException;
	Vector<String> getClientNames() throws RemoteException;
	Vector<String> getClientHashNumbers() throws RemoteException;
	Vector<String> getClientNumbers() throws RemoteException;
	Vector<String> getClientNonces() throws RemoteException;
	String getClientLedgers() throws RemoteException;
	void setClientLedger(long ts, String name, String hashNUmber, String nonce) throws RemoteException, NoSuchAlgorithmException;
	
}