package minesweeper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GameEndScreen extends JFrame{
	
	private static final long serialVersionUID = -2574832596366659625L;

	GUI gui;
	
	Point screen_size = new Point(250,250);
	
	Point names_position = new Point(40,140);
	Point names_size = new Point(250,25);
	
	Point scores_position = new Point(500,140);
	Point scores_size = new Point(100,25);
	
	BackgroundPanelClass background_panel;
	
	GameEndScreen(GUI g) throws IOException
	{
		super("High scores");
		gui = g;	
		setLayout(null);
		background_panel = new BackgroundPanelClass(Defines.backGroundImagePath);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(screen_size.x,screen_size.y);
		add(background_panel);
		setVisible(true);
		repaint();
	}
	
	public void setHighScore(Scores scoreTable)
	{
		List<Integer> scores = scoreTable.getScores();
		List<String> names = scoreTable.getNames();
		
		int maxScore = 0;
		String maxScoreName = "temp";
		int maxScorePos = -1;
		int tempPos = -1;
		
		background_panel.setSize(screen_size.x,screen_size.y);
		
		setSize(screen_size.x,screen_size.y);
		
	    ListIterator<Integer> score_it = scores.listIterator();
	    
	    while(score_it.hasNext()) {
	    	tempPos++;
	    	Integer score = (Integer) score_it.next();
	        JLabel element = new JLabel();
	        element.setLayout(null);
	        element.setText(score.toString());
	        element.setForeground(Color.GREEN);
	        element.setLocation(scores_position.x, scores_position.y + names_size.y*(score_it.nextIndex()-1));
	        element.setSize(scores_size.x,scores_size.y);
	        if(score > maxScore)
	        {
	        	maxScore = score;
	        	maxScorePos = tempPos;
	        }
			background_panel.add(element);
	    }
		
		ListIterator<String> name_it = names.listIterator();
		
		tempPos = -1;
		
	    while(name_it.hasNext()) {
	    	tempPos++;
	    	String name = (String) name_it.next();
	        JLabel element = new JLabel();
	        element.setLayout(null);
	        element.setText(name);
	        element.setForeground(Color.GREEN);
	        element.setLocation(names_position.x, names_position.y + names_size.y*(name_it.nextIndex()-1));
	        element.setSize(names_size.x,names_size.y);
			background_panel.add(element);
			if(tempPos == maxScorePos)
			{
				maxScoreName = name;
			}
	    }

	    String winString;
	    if( maxScoreName.compareTo(gui.myname) == 0)
	    {
	    	winString = "You won!";
	    }
	    else
	    {
	    	winString = "You failed!";
	    }
	    
	    JLabel win = new JLabel();
	    win.setLayout(null);
	    win.setText(winString);
	    win.setForeground(Color.GREEN);
	    win.setLocation(250, 50);
	    win.setSize(names_size.x*2,names_size.y*2);
	    win.setFont(new Font("Courier", Font.BOLD,26));
		background_panel.add(win);
	    
	    screen_size.x = scores_position.x + scores_size.x;
	    screen_size.y = scores.size()*scores_size.y + 250;
	    background_panel.setSize(new Dimension(screen_size.x, screen_size.y));
	    setSize(new Dimension(screen_size.x, screen_size.y));
		repaint();
	}
	
	
	 class BackgroundPanelClass extends JPanel{
		
		private static final long serialVersionUID = 502241893203685506L;
		private Image img;

		  public BackgroundPanelClass(String path) throws IOException {
			img = ImageIO.read(getClass().getResourceAsStream("/resources/background.png"));
		    setSize(screen_size.x,screen_size.y);
		    setLayout(null);
		  }

		  public void paintComponent(Graphics g) {
		    g.drawImage(img, 0, 0, null);
		  }
	}
	
}
