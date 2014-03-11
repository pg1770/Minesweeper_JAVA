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
	  GUI k = new GUI(x);
    x.setGUI(k);
    int [] [] fields = new int [4][5];
    
    for (int i = 0; i < 4 ; i++)
    {
      for(int j = 0; j < 5; j++)
      {
        fields[i][j] = i+j;
      }
    }
    
    x.set_new_minefield(fields);
    
		//x.GameStart();
	}

}
