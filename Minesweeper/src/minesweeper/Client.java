package minesweeper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Network{
	 
	 private Socket 			clientSocket = null; 
	 private ObjectOutputStream out = null;
	 private ObjectInputStream 	in = null;
	 private final int 			port = 9898; 
	 private String 			clientName;
	 private clientsList		clientList;
	 private Object 			fromServerObject;
	 
	 Client(Control c){
		 super(c);
	 }
	 	  
	 private class ReceiverThread implements Runnable{
		 public void run(){
			 System.out.println("Login to the server!");
			 try {
				 send(clientName);
				 while(((fromServerObject = in.readObject()) != null ) && clientSocket.isConnected()){
					 
					 if (fromServerObject.getClass() == clientsList.class){
						 clientList = (clientsList) fromServerObject;
						 //ctrl.clientReceived(clientList);
						 clientList.printClientList();
					 }
					 
					 if(fromServerObject.getClass() == String.class){
						 String fromServerString = (String) fromServerObject;
						 System.out.println(fromServerString); 
					 }
				 }
			 } catch (ClassNotFoundException classNot) {
			       System.err.println("data received in unknown format");
			 }	catch(Exception e){
				 System.err.println("Server disconnected!");
				 System.err.println(e.getMessage());
			 }	finally {
				 disconnect();
			 }
		}
	 }
	 
	 @Override
	 void connect(String ip, String name){
		 clientName=name;
		 disconnect();
		 try{
			 clientSocket = new Socket(ip, port);
			 out = new ObjectOutputStream(clientSocket.getOutputStream());
	         in = new ObjectInputStream(clientSocket.getInputStream());
	         out.flush();
	         clientList = new clientsList();
	         Runnable runnable = new ReceiverThread();
	    	 Thread thread = new Thread(runnable);
	    	 thread.start();
		 }	catch(UnknownHostException e) {
	         System.err.println("Don't know about host");
		 }  catch(Exception e){
			 System.err.println("Cannot connect to server!");
			 System.err.println(e.getMessage());
		 }
	 }

	 @Override
	 void send(Object p) {
		 if (out == null) return;
	     try {
	    	 out.writeObject(p);
	         out.flush();
	     }	catch (Exception e) {
	         System.err.println("Send error.");
	         System.err.println(e.getMessage());
	     }
	 }
	 
	 void send(String str){
		 if (out == null) return;
	     try {
	        out.writeObject(str);
	        out.flush();
	     }	catch (IOException e) {
	    	 System.err.println("Send error.");
	    	 System.err.println(e.getMessage());
	     }
	 }
	 
	 void disconnect() {
	        try {
	        	if (out != null) out.close();
	        	if (in != null) in.close();
	        	if (clientSocket != null) clientSocket.close();
	        } catch (IOException e) {
	        	System.err.println("Error while closing connection!");
	        	System.err.println(e.getMessage());
	        }
	    }
}
