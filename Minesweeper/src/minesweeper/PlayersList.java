package minesweeper;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PlayersList implements Serializable{

	private static final long serialVersionUID = -964787797619154595L;
	private Collection<Player> playerList= Collections.synchronizedList(new ArrayList<Player>());

	//Function to add a Player
	void addPlayer(Player playerInstance){
		playerList.add(playerInstance);
	}
	
	//Function to add a Player
	void addPlayer(int ID, String playerName, int tableSize){
		playerList.add(new Player(ID, playerName, tableSize));
	}
	
	//Function to change the tableSize
	void changeTableSize(int ID, int tableSize){
		for(Player actualPlayer : playerList){
			if(actualPlayer.getID() == ID){
				actualPlayer.setTableSize(tableSize);
			}
		}
	}
	
	//Function to print the playerList
	void printPlayersList(){
		for(Player actualPlayer : playerList){
			 actualPlayer.printPlayer();
		}
		System.out.println("");
	}
	
	void removePlayer(int ID){
		Collection<Player> found= Collections.synchronizedList(new ArrayList<Player>());
		for(Player actualPlayer : playerList){
			if(actualPlayer.getID() == ID){
				found.add(actualPlayer);
			}
		}
		playerList.removeAll(found);
	}
	
	int getTableSize(){
		return playerList.iterator().next().getTableSize();
	}
	
	boolean sameTableSize(){
		boolean start = true;
		int	actualTableSize = playerList.iterator().next().getTableSize();
		if (playerList.size() != 0){
			for(Player actualPlayer : playerList){
				if( ( actualPlayer.getTableSize() != 0 ) && ( actualPlayer.getTableSize() != actualTableSize)){
					start = false;
				} else if(actualPlayer.getTableSize() == 0){
					start = false;
				}
			}
		}	else{
			start = false;
		}
		return start;
	}
	
	Collection<Player>	getCollection(){
		return playerList;		
	}

}
