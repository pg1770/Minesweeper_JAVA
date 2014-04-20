package minesweeper;

import java.awt.Point;
import java.io.IOException;

import javax.swing.JFrame;

public class GUI extends JFrame {

	GameScreen gm_sc;
	StartScreen st_sc;
	GameEndScreen gm_e_sc;
	Control control;
	String myname;
	
	public void setPlayerName(String s)
	{
		if (s==null)
		{
			
		}
		myname = s;
		control.setMyName(s);
		
	}
	
	public GUI(Control c) throws IOException
	{
		control=c;
		 StartStartScreen();
	}
	
	void StartStartScreen() throws IOException
	{
		st_sc = new StartScreen(this);
	}
	
	public void StartEndScreen() throws IOException
	{
		gm_e_sc = new GameEndScreen(this);
	}
	
	public void StartGameScreen(int [][] fields) throws IOException
	{
		if(st_sc != null)
		{
			st_sc.close();
		}
		if (gm_sc == null)
		{
			gm_sc = new GameScreen(this,fields);
			///gm_sc.newFieldTable(fields);
			//gm_sc.setTime(0);
		}
		else
		{
			gm_sc.modifyFieldTable(fields);
			;
		}
	}
	
	
	void clickHappend(Point p, int mouse_event_num) throws IOException
	{
		ClickEvent click = new ClickEvent(p,mouse_event_num, myname);
		//control.sendClick(click);
		//temp csak a régi teszhez
		
		control.sendClick(click);
		;
	}
	

	
	//TODO Start_screennél
	public void printList(clientsList list)
	{
		st_sc.listNames(list);
	}
	
	public void chooseTableSize(int tableSize)
	{
		control.acceptGame(tableSize);
	}

	public void setNewTime(TimeStamp t) throws IOException
	{
		gm_sc.setTime(t.getTimeElapsedSecond());
	}
	
	public void showScores(Scores scoreTable) throws IOException
	{
		StartEndScreen();
		
		
		//
	}
}