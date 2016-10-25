# Lottery-system
Lottery-System using JavaRMI



This project is implemented using Java RMI technology. To Implement the lottery process I have
written 2 Programs. 1 for client and 1 for Block Chain server.
Server Program contains Lottery System capabilities like managing clients picking up the winner,
displaying results to the users etc. Server also implements the block chain which stores all the data such as
value chosen, hash value, timestamp etc.
Client Program Implements User capabilities and interactions with the server. Like sending the hash value
and number chosen, viewing the block chain etc.

RMI method works in Java using Interfaces. So, the interfaces for each functionality are created
first and then the server and client classes implements their corresponding interfaces.



Server.java - This implements all the methods declared above

public class Server extends UnicastRemoteObject implements
        ServerInterface {
    private static final long serialVersionUID = 1L;
    public volatile ArrayList<ClientInterface> clients = null;
    public volatile ArrayList<Block> blockChainLedger = null;
    private String prevHash = null;
    protected Server() throws RemoteException,
            NoSuchAlgorithmException {
        clients = new ArrayList<ClientInterface>();
        blockChainLedger = new ArrayList<Block>();
        prevHash = sha256("SHA-256");
    }
    @Override
    public synchronized void registerClient(ClientInterface client)
            throws RemoteException {
// TODO Auto-generated method stub
        this.clients.add(client);
    }
    public synchronized void unregisterChatClient(ClientInterface
                                                          client)
            throws RemoteException {
        this.clients.remove(this.clients.indexOf(client));
    }
    @Override
    public synchronized void sendMessage(String message) throws
            RemoteException {
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
//System.out.println("The Users registered in Lottery
        server are: ");
// Iterator itr = (Iterator) clients.iterator();
        Vector<String> v = new Vector<String>();
        int i =0;
        while(i<clients.size())
        {
            v.add(clients.get(i).getName());
// System.out.println(clients.get(i).getName());
            i++;
        }
        return v;
    }
    @Override
    public Vector<String> getClientHashNumbers() throws
            RemoteException {
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
// System.out.println(clients.get(i).getName());
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
// System.out.println(clients.get(i).getName());
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
            s+="Username - " +
                    this.blockChainLedger.get(i).getUsername() + "\n";
            s+="TimeStamp - " +
                    this.blockChainLedger.get(i).getTimeStamp()+ "\n";
            s+="Hash Number - " +
                    this.blockChainLedger.get(i).getHashNumber()+ "\n";
            s+="Nonce - " +
                    this.blockChainLedger.get(i).getNonce()+ "\n";
            s+="Previous Hash - " +
                    this.blockChainLedger.get(i).getPrevHash()+ "\n";
            s += "============================\n";
            i++;
        }
        return s;
    }
    @Override
    public void setClientLedger(long ts, String name, String
            hashNUmber, String nonce) throws RemoteException,
            NoSuchAlgorithmException{
        Block b = new Block();
        b.setTimeStamp(ts);
        b.setUsername(name);
        b.setHashNumber(hashNUmber);
        b.setPrevHash(this.prevHash);
        b.setNonce(nonce);
// compute hash of all these 4 values and perform hash
        again, store in prevHash Attribute of server.
                this.prevHash = sha256(name + hashNUmber +
                String.valueOf(ts) + this.prevHash);
        this.blockChainLedger.add(b);
    }
    static String sha256(String input) throws
            NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA-
                256");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) +
                    0x100, 16).substring(1));
        }
        return sb.toString();
    }
}


ServerMain.java:
This is the file that needs to be executed on server terminal.
On running this, the server waits for users to login into the system for 2 minutes. Once the users
are registered it waits for the hash values from the server. After the hash values are received the
server waits for the Actual values and nonce from the clients. Then the server calculates the hash
of the value and client and compares it with the hash value sent by the server. If the hash values
of all the sever matches, then the the server computes the values else the whole lottery process is
aborted.
During computation the server computes the value of (x+y+z)%3 and compares it with the value
of x%3,y%3 and z%3. Whose ever value matches is the winner. If there is more than one winner.
There can be more than 1 winner and the lottery process will be aborted.
Server stores all the values in the Block Chain which are connected with the hash values of the
previous block. The data is stored in each block and compares the hash values and the hash of
actual value + nonce from the client.
And finally server displays the winner to the each client. This process is continued for many
times.
Client Program:
The client program allows the members to participate in the lottery, when a user runs the client
program it asks for the user id and the value to be passed. Then the client wll generate the hash
value and send it to the server and finally sends the actual value along with the nonce
Clientinterface.java:
For implementing this initially, I have created the Serverinterface.java which declares all the
methods which will be executed by the server.

public interface ClientInterface extends Remote{
void receiveMessage(String message) throws RemoteException;
String getName() throws RemoteException;
String getHashNumber() throws RemoteException;
String getNumber() throws RemoteException;
String getNonce() throws RemoteException;
// void receiveNumber(String number) throws RemoteException;
}

Client.java - This implements all the methods declared above .


package com.lottery.rmi.client;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import com.lottery.rmi.server.ServerInterface;
public class Client extends UnicastRemoteObject implements ClientInterface {
    public String name = null;
    public ServerInterface server;
    public String hashNumber = null;
    public String number = null;
    public String nonce = null;
    protected Client(String name, String number, ServerInterface server) throws
            RemoteException {
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


ClientMain.java: Immediately after running this it asks for the user id and the value to be
passed. Then the client wll generate the hash value and send it to the server and finally sends the
actual value along with the nonce.
The client also view the whole block chain stored in the server so that it can cross check if
required.
The client will be able to send the values and view the results. If the client sends the wrong value
after sending the hash value. Then the server will abort the whole process because it was a
mismatch.



