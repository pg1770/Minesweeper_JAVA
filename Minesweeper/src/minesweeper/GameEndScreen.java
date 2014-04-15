package minesweeper;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


import minesweeper.GameScreen.Button;

public class GameEndScreen extends JFrame{

	
	GUI gui;
	
	
	final int screen_size_x = 250;
	final int screen_size_y = 250;
	
	BackgroundPanelClass background_panel;
	
	void close()
	{
		dispose();
	}
	
	public void listNames(clientsList list)
	{
		
	}
	
	
	
	GameEndScreen(GUI g) throws IOException
	{
		super("Start minesweeper");
		gui = g;
			
		
		setLayout(null);
		background_panel = new BackgroundPanelClass("resources\\background.png");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(screen_size_x,screen_size_y);
		add(background_panel);
		setVisible(true);
		repaint();
		
		
	}
	
	public void setHighScore(Scores scoreTable)
	{
		
	}
	
	
	 class BackgroundPanelClass extends JPanel{
		
		  private Image img;


		  public BackgroundPanelClass(String path) throws IOException {
			img = ImageIO.read(new File(path));
		    setSize(screen_size_x,screen_size_y);
		    setLayout(null);
		  }

		  public void paintComponent(Graphics g) {
		    g.drawImage(img, 0, 0, null);
		  }
	}
	
}
