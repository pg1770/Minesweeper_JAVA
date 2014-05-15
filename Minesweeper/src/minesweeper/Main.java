package minesweeper;

import java.io.IOException;

import minesweeper.Control;
import minesweeper.GUI;

public class Main {

	public static void main(String[] args) throws IOException {
		
		Control x = new Control();
	  GUI k = new GUI(x);
    x.setGUI(k);
 
		/*
		Scores sc = new Scores();
		sc.addUser("TEST");
		sc.addScoreToUser(25, "TEST");
		GUI g = new GUI(sc);
		*/
		
	}

}
