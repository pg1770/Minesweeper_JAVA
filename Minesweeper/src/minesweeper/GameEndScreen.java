package minesweeper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


import minesweeper.GameScreen.Button;

public class GameEndScreen extends JFrame{
	
	GUI gui;
	
	Point screen_size = new Point(250,250);
	
	
	Point names_position = new Point(40,140);
	Point names_size = new Point(250,25);
	
	Point scores_position = new Point(500,140);
	Point scores_size = new Point(100,25);
	
	BackgroundPanelClass background_panel;
	
	List<Integer> scores;
	List<String> names;
	
	JLabel [] namesLabels;
	JLabel [] scoresLabels;
	
	void close()
	{
		dispose();
	}
	

	
	
	GameEndScreen(GUI g) throws IOException
	{
		super("High scores");
		gui = g;
			
		
		setLayout(null);
		background_panel = new BackgroundPanelClass("resources\\background.png");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(screen_size.x,screen_size.y);
		add(background_panel);
		setVisible(true);
		repaint();
		
		
	}
	
	public void setHighScore(Scores scoreTable)
	{
		scores = scoreTable.getScores();
		names = scoreTable.getNames();
		
		int line_num = names.size();
		namesLabels = new JLabel[line_num];
		scoresLabels = new JLabel[line_num];
		
		screen_size.x = scores_position.x + scores_size.x + 100;
		screen_size.y = scores_position.y + scores_size.y*line_num + 100;
		
		background_panel.setSize(screen_size.x,screen_size.y);
		
		setSize(screen_size.x,screen_size.y);
		
		for(int i=0; i<line_num; i++)
		{
			JLabel temp = new JLabel();
			JLabel temp2 = new JLabel();
			namesLabels[i] = temp;
			temp.setLayout(null);
			temp.setText(names.get(i));
			temp.setForeground(Color.GREEN);
			temp.setLocation(names_position.x, names_position.y + names_size.y*i);
			temp.setSize(names_size.x,names_size.y);
			background_panel.add(temp);
			//temp.setVisible(true);
			
			temp2 = new JLabel();
			scoresLabels[i] = temp2;
			temp2.setLayout(null);
			temp2.setText(scores.get(i).toString());
			temp2.setForeground(Color.GREEN);
			temp2.setLocation(scores_position.x, scores_position.y + scores_size.y*i);
			temp2.setSize(scores_size.x,scores_size.y);
			background_panel.add(temp2);
			//temp.setVisible(true);
			
		}
				
		repaint();
	}
	
	
	 class BackgroundPanelClass extends JPanel{
		
		  private Image img;


		  public BackgroundPanelClass(String path) throws IOException {
			img = ImageIO.read(new File(path));
		    setSize(screen_size.x,screen_size.y);
		    setLayout(null);
		  }

		  public void paintComponent(Graphics g) {
		    g.drawImage(img, 0, 0, null);
		  }
	}
	
}
