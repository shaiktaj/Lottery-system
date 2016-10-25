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


ClientMain.java: Immediately after running this it asks for the user id and the value to be
passed. Then the client wll generate the hash value and send it to the server and finally sends the
actual value along with the nonce.
The client also view the whole block chain stored in the server so that it can cross check if
required.
The client will be able to send the values and view the results. If the client sends the wrong value
after sending the hash value. Then the server will abort the whole process because it was a
mismatch.



