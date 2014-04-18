package minesweeper;

import java.awt.Point;
import java.io.Serializable;

public class ClickEvent implements Serializable{
	
	/**
   * 
   */
  private static final long serialVersionUID = 2313781298361655323L;
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
