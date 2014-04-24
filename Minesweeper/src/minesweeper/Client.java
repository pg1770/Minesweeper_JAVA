package minesweeper;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Control{
	 
	 private Socket 			clientSocket = null; 
	 private ObjectOutputStream out = null;
	 private ObjectInputStream 	in = null;
	 private Object 			fromServerObject;
	 protected Control 			ctrl;
		
	 protected Client(Control c){
			ctrl = c;
	 }
	 	  
	 private class ReceiverThread implements Runnable{
		 public void run(){
			 try {
				 while(((fromServerObject = in.readObject()) != null ) && clientSocket.isConnected()){
					 
					 if(fromServerObject.getClass() == Scores.class){
						 Scores scoreTable = (Scores) fromServerObject;
						 ctrl.receiveScores(scoreTable);
					 }
					 
					 if(fromServerObject instanceof TimeStamp){
						 TimeStamp timeStamp = (TimeStamp) fromServerObject;
						 ctrl.receiveGameTime(timeStamp);
					 }
					 
					 if(fromServerObject instanceof GameInfo){
						 GameInfo gameInfo = (GameInfo) fromServerObject;
						 ctrl.receiveGameInfo(gameInfo);
					 }
					 
					 if(fromServerObject instanceof PlayersList){
						 PlayersList players = (PlayersList) fromServerObject;
						 ctrl.playersListPrint(players);
					 }
 
				 }
			 } catch (ClassNotFoundException classNot) {
				 ctrl.clientError("data received in unknown format");  
			 }	catch(Exception e){
				 ctrl.clientError("Server disconnected!");
			 }	finally {
				 disconnect();
			 }
		}
	 }
	 
	 void connect(){
		 disconnect();
		 try{
			 clientSocket = new Socket(NetworkDefines.ip, NetworkDefines.port);
			 out = new ObjectOutputStream(clientSocket.getOutputStream());
	         in = new ObjectInputStream(clientSocket.getInputStream());
	         out.flush();
	         Thread thread = new Thread(new ReceiverThread());
	         thread.start();
		 }	catch(UnknownHostException e) {
			 ctrl.clientError("Don't know about host");
		 }  catch(Exception e){
			 ctrl.clientError("Cannot connect to server!");
		 }
	 }
	 
	 void setName(String clientName){
		 send(clientName);
	 }

	 void send(String str){
		 if (out == null) return;
	     try {
	        out.writeObject(str);
	        out.flush();
	        out.reset();
	     }	catch (IOException e) {
	    	 ctrl.clientError("Send error!");
	     }
	 }
	 
	 void send(int tableSize){
		 if (out == null) return;
	     try {
	        out.writeObject(tableSize);
	        out.flush();
	        out.reset();
	     }	catch (IOException e) {
	    	 ctrl.clientError("Send error!");
	     }
	 }
	 
	 void send(ClickEvent	clickEvent){
		 if (out == null) return;
	     try {
	        out.writeObject(clickEvent);
	        out.flush();
	        out.reset();
	     }	catch (IOException e) {
	    	 ctrl.clientError("Send error!");
	     }
	 }
	  
	 void disconnect() {
		 try {
			 if (out != null) out.close();
			 if (in != null) in.close();
			 if (clientSocket != null) clientSocket.close();
		 } catch (IOException e) {
			 ctrl.clientError("Error while closing connection!");
	     }
	 }
}
