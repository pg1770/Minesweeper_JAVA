package minesweeper;

import java.io.IOException;

import minesweeper.Control;
import minesweeper.GUI;

import java.io.*;

public class Main {

	public static void main(String[] args) throws IOException {
		// startGame();
		System.out.println();
		Control c = new Control();
		GUI g = new GUI(c);
		c.setGUI(g);
		Control x = new Control(10,10,20);
		x.GameStart();
	}

}
