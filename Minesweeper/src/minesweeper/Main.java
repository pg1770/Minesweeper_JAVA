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
//		GUI g = new GUI(c);
//		c.setGUI(g);
    //String path = "im.jpg";
    //BufferedImage im = ImageIO.read(new File(path));
    //Point pos = new Point(20,10);
//g.paint_image(im, pos);
//c.set_new_minefield(5, 10);
<<<<<<< HEAD

//		c.set_new_minefield(5, 10);
=======
//ez látszik tomis??:D
		// ti ennyit írtatok??????????
		///dasff
		c.set_new_minefield(5, 10);
>>>>>>> 9da9dc0827bb6733d41bacd109761a02e71604b9
		Game x = new Game(10,10,20);
		x.GameStart();
		
	}

}
