package minesweeper;

import java.io.Serializable;
import java.util.ArrayList;

public class clientsList implements Serializable{

	private static final long serialVersionUID = 2296359732912201133L;
	private ArrayList<clients> clientList;
	
	clientsList() {
		clientList = new ArrayList<clients>();
	};
	
	void addClient(clients	clientInstance){
		clientList.add(clientInstance);
	};
	
	void addClient(int n, String s){
		clients client=  new clients(n, s);
    	addClient(client);
	};
	
	public ArrayList<clients> getClientList()
	{
		return clientList;
	}
	
	void printClientList(){
		System.out.println("");
		for(int i=0; i< clientList.size();i++)
			System.out.println(clientList.get(i).getID() + ". " + clientList.get(i).getclientName());
	};
	
	protected clientsList(clientsList original){
		clientList = new ArrayList<clients>(original.clientList);
	}
	
	public clientsList copy(){
		return new clientsList(this);
	}
}
