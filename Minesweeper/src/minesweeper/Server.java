package minesweeper;

import java.net.*;
import java.io.*;

public class Server extends Network{ 

	private	ServerSocket	listener = null;
	private clientsList	   	clientList;
	private final int		port=9898;
	private String			serverName;
	private int 			count=0;
	
	protected Server(Control c){
		super(c);
	}
	
	public class ReceiverThread implements Runnable  {
		private	Socket				connection = null;
		private int 				ID;
		private ObjectOutputStream 	out = null;
		private ObjectInputStream	in = null;
		
		ReceiverThread(Socket socket, int serialNumber) {
			this.connection = socket;
	    	this.ID = serialNumber;
		}
		
		public void run(){
		   	try{
		   		in =  new ObjectInputStream (connection.getInputStream());
		    	out= new ObjectOutputStream(connection.getOutputStream());
		   	}	catch(Exception e){
		   		System.err.println("Error while getting streams.");
		   		System.err.println(e.getMessage());
		   		disconnect();
		   		return;
		   	}
		   	try{
		   		while(true) {
		   			String fromClient = (String) in.readObject();
	    	    	clientList.addClient(ID, fromClient);
	    	    	ctrl.clientReceived(clientList);
	    	    	send(clientList.copy());
		   		}
	    	}	catch(Exception e)	{
	    		System.out.println(e.getMessage());
                System.err.println("Client disconnected!");
	    	}	finally	{
	    		try{
	    			connection.close();
	    		}	catch(IOException e)	{
	    			System.err.println("Error while closing connection!");
		        	System.err.println(e.getMessage());
	    		}
	    	}
		}
		
		void send(clientsList p) {
	    	if (out == null) return;
	        try {
	            out.writeObject(p);
	            out.flush();
	        } catch (Exception e) {
	            System.err.println("Send error.");
	            System.err.println(e.getMessage());
	        }
	    }
		
		void send(String p) {
	    	if (out == null) return;
	        try {
	            out.writeObject(p);
	            out.flush();
	        } catch (Exception e) {
	            System.err.println("Send error.");
	            System.err.println(e.getMessage());
	        }
	    }
		
		void send(Object p) {
	    	if (out == null) return;
	        try {
	            out.writeObject(p);
	            out.flush();
	        } catch (Exception e) {
	            System.err.println("Send error.");
	            System.err.println(e.getMessage());
	        }
	    }
	
		void disconnect() {
	        try {
	        	if (out != null) 		out.close();
	            if (in != null) 		in.close();
	            if (connection != null) connection.close();
	        } catch (IOException e) {
	        	System.err.println("Error while closing connection!");
	            System.err.println(e.getMessage());
	        }
	    }
	
	}

	
	@Override
	void connect(String ip, String name){
		serverName=name;
		disconnect();
		try{
			listener = new ServerSocket(port);
			System.out.println("Server initialized!");
    		clientList = new clientsList();
    		clientList.addClient(++count, serverName);
    		clientList.printClientList();
		}	catch(IOException e){
			System.err.println("Could not listen on port: " + port);
			System.err.println(e.getMessage());
		}	
    	try {
    		while(true){
    			Runnable runnable = new ReceiverThread(listener.accept(), ++count);
    			Thread thread = new Thread(runnable);
    			thread.start();
			}
		}	catch(IOException e){
				System.err.println("Accept failed!");
				System.err.println(e.getMessage());
				disconnect();
				return;
		}
	}
			
	@Override
    void send(Object p) {}
	
	@Override
	void disconnect() 	{}
}