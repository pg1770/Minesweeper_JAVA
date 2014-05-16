package minesweeper;

import java.io.IOException;

import minesweeper.Control;
import minesweeper.GUI;

public class Main {

	public static void main(String[] args) throws IOException {
	  if(args.length != 0) NetworkDefines.ip = args[0];
		Control x = new Control();
	  GUI k = new GUI(x);
    x.setGUI(k);		
	}

}
