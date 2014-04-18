package minesweeper;

import java.io.Serializable;

public class Player implements Serializable {
		
		private static final long serialVersionUID = 2842478912251863822L;
		private String 	playerName;
		private int		ID;
		private int		tableSize;
		
		Player(int ID, String playerName, int tableSize){
			this.ID = ID;
			this.playerName = playerName;
			this.tableSize = tableSize;
		}
		
		int 	getID(){
			return ID;
		}
		
		void setID(int ID){
			this.ID = ID;
		}
		
		String	getPlayerName(){
			return playerName;
		}
		
		void setPlayerName(String playerName){
			this.playerName = playerName;
		}
		
		int getTableSize(){
			return tableSize;
		}
		
		void setTableSize(int tableSize){
			this.tableSize=tableSize;
		}
		
		void printPlayer(){
			System.out.println(ID + ". " + playerName + ": " + tableSize);
		}
		
	}

