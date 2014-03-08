package minesweeper;

import javax.swing.JFrame;

public class Server_screen extends JFrame{

	GUI gui;
	final int screen_size_x = 250;
	final int screen_size_y = 250;
	
	public Server_screen (GUI g)
	{
		super("Minesweeper server");
		gui = g;
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(screen_size_x,screen_size_y);
		setVisible(true);
		
	}
	
}
