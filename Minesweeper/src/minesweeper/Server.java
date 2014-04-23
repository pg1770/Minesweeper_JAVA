package minesweeper;

import java.net.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.io.*;

public class Server extends Control{ 

	private	ServerSocket						serverSocket = null;
	private static Collection<ReceiverThread>	receiverList = null;
	private static PlayersList					players = null;
	private int									count=0;
	protected Control 							ctrl;
	
	protected Server(Control c){
		ctrl = c;
	}
	
	class ReceiverThread implements Runnable {
		private	Socket				connection = null;
		private int 				ID;
		private ObjectOutputStream 	out = null;
		private ObjectInputStream	in = null;
		private Object				fromClientObject;
		
		ReceiverThread(Socket socket, int ID) {
			this.connection = socket;
	    	this.ID = ID;
	    	try{
    			in = new ObjectInputStream (connection.getInputStream());
    			out= new ObjectOutputStream(connection.getOutputStream());
    		}	catch(Exception e){
    				ctrl.serverError("Error while getting streams.");
    				disconnect();
    				return;
    		}
		}
		
		public void run() {
			try{
				while(((fromClientObject = in.readObject()) != null ) && connection.isConnected()){

					if(fromClientObject instanceof String){
						synchronized(players){
							String playerName = (String) fromClientObject;
							players.addPlayer(ID, playerName, 0);
							broadcast(players);
						}
					}
					
					if(fromClientObject instanceof Integer){
						synchronized(players){
							int	tableSize = (int) fromClientObject;
							players.changeTableSize(ID, tableSize);
							broadcast(players);
						}
					}
				
					if(fromClientObject instanceof ClickEvent){
						ClickEvent click = (ClickEvent) fromClientObject;
						ctrl.receiveClick(click);
					}
				
					checkToGameStart();
				
				}
			}	catch(Exception e)	{
					ctrl.serverError("Client disconnected!");
			}	finally	{
					try{
						System.out.println(ID);
						synchronized(receiverList){
							Collection<ReceiverThread> found= Collections.synchronizedList(new ArrayList<ReceiverThread>());
							for(ReceiverThread actualReceiver : receiverList){
								if(actualReceiver.getID() == ID){
									found.add(actualReceiver);
								}
							}
							receiverList.removeAll(found);	
						}
						synchronized(players){
							players.removePlayer(ID);
						}
						broadcast(players);
						connection.close();
					}	catch(IOException e)	{
							ctrl.serverError("Error while closing connection!");
					}
			}
		}
	
		void send(PlayersList players){
			if(out == null)	return;
			try {
	            out.writeObject(players);
	            out.flush();
	            out.reset();
	        } catch (Exception e) {
	            ctrl.serverError("PlayersList Send error!");
	        }
		}
		
		void send(GameInfo gameInfo){
			if(out == null)	return;
			try {
	            out.writeObject(gameInfo);
	            out.flush();
	            out.reset();
	        } catch (Exception e) {
	            ctrl.serverError("GameInfo Send error!");
	        }
		}
		
		void send(TimeStamp timeStamp){
			if(out == null)	return;
			try {
	            out.writeObject(timeStamp);
	            out.flush();
	            out.reset();
	        } catch (Exception e) {
	            ctrl.serverError("TimeStamp Send error!");
	        }
		}
		
		void send(Scores scoreTable){
			if(out == null)	return;
			try {
	            out.writeObject(scoreTable);
	            out.flush();
	            out.reset();
	        } catch (Exception e) {
	            ctrl.serverError("Scores Send error!");
	        }
		}
		
		int	getID(){
			return ID;
		}
		
		void disconnect() 	{   
			try {
				if (out != null) out.close();
				if (in != null) in.close();
				if (connection != null) connection.close();
			} catch (IOException ex) {
				ctrl.serverError("Error while closing connection with client!");
			}
		}
	}
	
	void connect(){
		receiverList = new ArrayList<ReceiverThread>();
		players = new PlayersList();
		disconnect();
		try{
			serverSocket = new ServerSocket(NetworkDefines.port);
		}	catch(IOException e){
				ctrl.serverError("Could not listen on port: " + NetworkDefines.port);
		}	
		try {
			while(true){
				Socket s = serverSocket.accept();
				ReceiverThread r= new ReceiverThread(s, ++count);
				receiverList.add(r);
				Thread t = new Thread(r);
				t.start();
			}
		}	catch(IOException e){
				ctrl.serverError("Accept failed!");
				disconnect();
		}
	}
	
	int counter = 0;
	
	void checkToGameStart() throws IOException{
			if(players.sameTableSize()){
				System.out.println("GameStarted in server");
				 System.out.println(players.getTableSize());
				if(counter == 0) { ctrl.GameStart(players.getTableSize()); ++counter; }
			}
	}
	
	void broadcast(PlayersList players){
		for(ReceiverThread actualThread : receiverList){
				actualThread.send(players);
			}
	}
	
	void broadcast(GameInfo	gameInfo){
		for(ReceiverThread actualThread : receiverList){
				actualThread.send(gameInfo);
			}
	}
	
	void broadcast(TimeStamp timeStamp){
		for(ReceiverThread actualThread : receiverList){
				actualThread.send(timeStamp);
			}
	}
	
	void broadcast(Scores scoreTable){
		for(ReceiverThread actualThread : receiverList){
				actualThread.send(scoreTable);
			}
	}
	
	void disconnect() 	{   
		try {
			if (serverSocket !=null) serverSocket.close();
		} catch (IOException ex) {
			ctrl.serverError("Error while closing server!");
		}
	}
}