package minesweeper;

import java.awt.Point;

public class ClickEvent {
	
	Point p;
	int mouse_event_num;
	String myname;
	
	ClickEvent(Point p, int mouse_event_num,String myname)
	{
		this.p = p;
		this.mouse_event_num = mouse_event_num;
		this.myname = myname;
	}
}
