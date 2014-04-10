package minesweeper;

import java.awt.Point;
import java.io.IOException;

import javax.swing.JFrame;

public class GUI extends JFrame {

	Game_screen gm_sc;
	Start_screen st_sc;
	Server_screen sv_sc;
	String player;
	Control control;
	
	public void set_player_name(String s)
	{
		if (s==null)
			
			
		player = s;
	}
	
	public GUI(Control c) throws IOException
	{
		control=c;
	}
	
	public void Start_start_screen()
	{
		st_sc = new Start_screen(this);
	}
	
	public void Start_server_screen()
	{
		st_sc.close();
		sv_sc = new Server_screen(this);
	}
	
	public void Start_client_screen() throws IOException
	{
		//st_sc.close();

	}
	
	public void Start_game_screen(int [][] fields) throws IOException
	{
		if (gm_sc == null)
		{
			gm_sc = new Game_screen(this);
			gm_sc.new_field_table(fields);
		}
		else
		{
			gm_sc.modify_field_table(fields);
			;
		}
	}
	
	public void screen(int screen_num) throws IOException
	{
		//gm_sc = new Game_screen(10, 20);
		st_sc = new Start_screen(this);
	}
	
	void click_happend(Point p, int mouse_event_num) throws IOException
	{
		control.click_happend(p, mouse_event_num);
// clickeventte atkurni az egeszet
		;
	}

}