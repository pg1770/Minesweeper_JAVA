package minesweeper;


import java.awt.Point;
import java.io.IOException;

public class Control {

	GUI gui;
	
	public void setGUI(GUI gui)
	{
		this.gui = gui;
	}
	
	public void set_new_minefield(int x, int y) throws IOException
	{
		gui.set_new_Size(x, y);
	}
	
	public void click_happend(Point p)
	{
		System.out.println(p.x +" " +p.y);
	}
}
