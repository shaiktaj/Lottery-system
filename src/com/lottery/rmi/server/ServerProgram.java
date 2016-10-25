package com.lottery.rmi.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ServerProgram {
public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
	
		String rmiURL = "rmi://localhost/lotteryServer";
	    ServerInterface server = (ServerInterface) Naming.lookup(rmiURL);
	    
	    server.getClientNames();	
	}
}
