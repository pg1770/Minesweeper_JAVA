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
//		c.setGUI(g);
    //String path = "im.jpg";
    //BufferedImage im = ImageIO.read(new File(path));
    //Point pos = new Point(20,10);
//g.paint_image(im, pos);
//c.set_new_minefield(5, 10);
		c.set_new_minefield(5, 10);
//		c.set_new_minefield(5, 10);
		Game x = new Game(10,10,20);
		x.GameStart();
//		
	}

}
