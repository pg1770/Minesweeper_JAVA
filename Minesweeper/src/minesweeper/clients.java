package minesweeper;

import java.io.Serializable;

public class clients implements Serializable {
	
	private static final long serialVersionUID = -8057278846902914050L;
	private String 	clientName;
	private int		ID;
	
	clients(int n, String s){
		this.clientName=s;
		this.ID=n;
	}
	
	int 	getID(){
		return ID;
	}
	
	String	getclientName(){
		return clientName;
	}
}
