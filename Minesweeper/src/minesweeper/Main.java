package minesweeper;

import java.io.IOException;

import minesweeper.Control;
import minesweeper.GUI;

import java.io.*;

public class Main {

	public static void main(String[] args) throws IOException {
		
		Control x = new Control(10,10,20);
	  GUI k = new GUI(x);
    x.setGUI(k);
    x.GameStart(2);
    x.timer.schedule(new TimeStamp(), 0, 1000 );
    
    if ((args.length > 1) ){
    	if(args[0].equals("s")){
    		x.startServer();
    	}	else if(args[0].equals("c")){
    		x.startClient();	
    	}
    }
    
	}

}
