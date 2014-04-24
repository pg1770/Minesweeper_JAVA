package minesweeper;

import java.io.IOException;

import minesweeper.Control;
import minesweeper.GUI;

public class Main {

	public static void main(String[] args) throws IOException {
		
		Control x = new Control();
	  GUI k = new GUI(x);
    x.setGUI(k);
 
	}

}
