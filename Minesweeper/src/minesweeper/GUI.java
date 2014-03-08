package minesweeper;

import java.io.IOException;

import javax.swing.JFrame;

public class GUI extends JFrame {

	Game_screen gm_sc;
	Start_screen st_sc;
	Server_screen sv_sc;
	String player;
	
	public void set_player_name(String s)
	{
		if (s==null)
			
			
		player = s;
	}
	
	public GUI(Control c) throws IOException
	{
		
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
	
	public void Start_client_screen()
	{
		st_sc.close();
		
	}
	
	public void Start_game_screen()
	{
		
	}
	
	public void screen(int screen_num) throws IOException
	{
		//gm_sc = new Game_screen(10, 20);
		st_sc = new Start_screen(this);
	}

}