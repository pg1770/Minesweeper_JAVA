package minesweeper;

import java.awt.Point;
import java.io.IOException;
import javax.swing.JOptionPane;

public class GUI {

	//J�t�kk�perny�
	GameScreen gm_sc;
	//Kezd�k�perny�
	StartScreen st_sc;
	//Eredm�nyk�perny�
	GameEndScreen gm_e_sc;
	//A control oszt�ly, amivel a kapcsolatot tartjuk
	Control control;
	//A j�t�kos neve
	String myname;
	
	
	//Konstruktor, a control oszt�ly referenci�ja elengedhetetlen a m�k�d�shez
	public GUI(Control c) throws IOException
	{
		control = c;
		StartStartScreen();
	}
	
	//
	// A kezd�k�perny�vel kapcsolatos met�dusok
	//
	
	//A kezd�k�perny�n megt�rt�nt a j�t�kos nev�nek be�ll�t�sa
	public void setPlayerName(String s)
	{
		if (s == null)
		{
			return;
		}
		myname = s;
		control.setMyName(s);
	}
	
	//Kezd�k�perny� ind�t�sa
	void StartStartScreen() throws IOException
	{
		st_sc = new StartScreen(this);
	}

	//Szerver ind�t�s�ra kattintott a felhaszn�l�
	public void server_click()
	{
		control.startServer();
	}
	
	//Kliens ind�t�s�ra kattintott a felhaszn�l�
	public void client_click()
	{
		control.startClient();
	}
	
	
	//Kiv�lasztott mez�m�ret elk�ld�se
	public void chooseTableSize(int tableSize)
	{
		control.acceptGame(tableSize);
	}
	
	
	//Csatlakozott j�t�kozok megjelen�t�se a kliens kezd�k�perny�n
	public void printList(PlayersList list)
	{
		if(st_sc == null)
			 JOptionPane.showMessageDialog(null, "Start screen doesn't exist");
		st_sc.listNames(list);
	}
	
	//A szerver k�perny��n logok megjelen�t�se
	public void printServerLog(String log)
	{
		if(st_sc == null)
			 JOptionPane.showMessageDialog(null, "Start screen doesn't exist");
		
		st_sc.printLog(log);
	}
	
	//
	// Az eredm�nyk�perny�vel kapcsolatos met�dusok
	//
	
	//Eredm�nyk�perny� ind�t�sa
	void StartEndScreen()
	{
		if(gm_sc != null)
		{
			gm_sc.dispose();
		}
		
		if(gm_e_sc == null)
		{
			try {
				gm_e_sc = new GameEndScreen(this);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "ERROR during create new GameEndScreen");
				e.printStackTrace();
			}
		}
		
	}
	
	//Eredm�ynek megjelen�t�se, azaz az eredm�nyk�perny� ind�t�sa �s eredm�nyek ki�r�sa
	public void showScores(Scores scoreTable) throws IOException
	{
		
		StartEndScreen();
		gm_e_sc.setHighScore(scoreTable);
	}
	
	
	//
	// A j�t�kk�perny�vel kapcsoatos met�dusok
	//
	
	//J�t�kk�perny� ind�t�sa, vagy friss�t�se
	public void SetGameScreen(int [][] fields) 
	{
		if(st_sc != null)
		{
			st_sc.dispose();
		}
		if (gm_sc == null)
		{
			gm_sc = new GameScreen(this,fields);
		}
		else
		{
			try {
				gm_sc.modifyFieldTable(fields);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "ERROR during create new modifyFieldTable");
				e.printStackTrace();
			}
		}
	}
	
	//Mez�re kattint�s t�rt�nt a j�t�kk�perny�n
	void clickHappenedOnGameScreen(Point p, int mouse_event_num) throws IOException
	{
		ClickEvent click = new ClickEvent(p,mouse_event_num, myname);
		control.sendClick(click);
	}
	
	//Az id�sz�ml�l� friss�t�se
	public void setNewTime(TimeStamp t) throws IOException
	{
		if (gm_sc == null)
			return;
		gm_sc.setTime(t.getTimeElapsedSecond());
	}
	
}