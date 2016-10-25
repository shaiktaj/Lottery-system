package com.lottery.rmi.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote{
	void receiveMessage(String message) throws RemoteException;
	String getName() throws RemoteException;
	String getHashNumber() throws RemoteException;
	String getNumber() throws RemoteException;
	String getNonce() throws RemoteException;

}
