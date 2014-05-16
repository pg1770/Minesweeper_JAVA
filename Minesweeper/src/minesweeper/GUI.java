package minesweeper;

import java.awt.Point;
import java.io.IOException;
import javax.swing.JOptionPane;

public class GUI {

	//Játékképernyõ
	GameScreen gm_sc;
	//Kezdõképernyõ
	StartScreen st_sc;
	//Eredményképernyõ
	GameEndScreen gm_e_sc;
	//A control osztály, amivel a kapcsolatot tartjuk
	Control control;
	//A játékos neve
	String myname;
	
	
	//Konstruktor, a control osztály referenciája elengedhetetlen a mûködéshez
	public GUI(Control c) throws IOException
	{
		control = c;
		StartStartScreen();
	}
	
	//
	// A kezdõképernyõvel kapcsolatos metódusok
	//
	
	//A kezdõképernyõn megtörtént a játékos nevének beállítása
	public void setPlayerName(String s)
	{
		if (s == null)
		{
			return;
		}
		myname = s;
		control.setMyName(s);
	}
	
	//Kezdõképernyõ indítása
	void StartStartScreen() throws IOException
	{
		st_sc = new StartScreen(this);
	}

	//Szerver indítására kattintott a felhasználó
	public void server_click()
	{
		control.startServer();
	}
	
	//Kliens indítására kattintott a felhasználó
	public void client_click()
	{
		control.startClient();
	}
	
	
	//Kiválasztott mezõméret elküldése
	public void chooseTableSize(int tableSize)
	{
		control.acceptGame(tableSize);
	}
	
	
	//Csatlakozott játékozok megjelenítése a kliens kezdõképernyõn
	public void printList(PlayersList list)
	{
		if(st_sc == null)
			 JOptionPane.showMessageDialog(null, "Start screen doesn't exist");
		st_sc.listNames(list);
	}
	
	//A szerver képernyõén logok megjelenítése
	public void printServerLog(String log)
	{
		if(st_sc == null)
			 JOptionPane.showMessageDialog(null, "Start screen doesn't exist");
		
		st_sc.printLog(log);
	}
	
	//
	// Az eredményképernyõvel kapcsolatos metódusok
	//
	
	//Eredményképernyõ indítása
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
	
	//Eredméynek megjelenítése, azaz az eredményképernyõ indítása és eredmények kiírása
	public void showScores(Scores scoreTable) throws IOException
	{
		
		StartEndScreen();
		gm_e_sc.setHighScore(scoreTable);
	}
	
	
	//
	// A játékképernyõvel kapcsoatos metódusok
	//
	
	//Játékképernyõ indítása, vagy frissítése
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
	
	//Mezõre kattintás történt a játékképernyõn
	void clickHappenedOnGameScreen(Point p, int mouse_event_num) throws IOException
	{
		ClickEvent click = new ClickEvent(p,mouse_event_num, myname);
		control.sendClick(click);
	}
	
	//Az idõszámláló frissítése
	public void setNewTime(TimeStamp t) throws IOException
	{
		if (gm_sc == null)
			return;
		gm_sc.setTime(t.getTimeElapsedSecond());
	}
	
}