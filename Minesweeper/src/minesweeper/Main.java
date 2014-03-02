package minesweeper;

import java.io.IOException;

import minesweeper.Control;
import minesweeper.GUI;

public class Main {

	public static void main(String[] args) throws IOException {
		// startGame();
		System.out.println();
		Control c = new Control();
		GUI g = new GUI(c);
		c.setGUI(g);
		c.set_new_minefield(5, 10);
	}

}
