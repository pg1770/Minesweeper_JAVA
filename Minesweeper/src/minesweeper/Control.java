package minesweeper;

import javax.swing.JOptionPane;

import java.awt.Point;
import java.io.IOException;


public class Control {  
  
  public int status = 0;
  public long startTime;
  public int clickNo=0;
  
  public Model model;
  
	GUI gui;
	
	/**CONSTRUCTORS**/
	
	public Control(int width, int height, int minesNo){
    model = new Model(width, height, minesNo);
    startTime = System.nanoTime();
  }
	
	public Control(){}
	
	/**ACCESS METHODS**/
	
  public void setGUI(GUI gui)
  {
    this.gui = gui;
  }
  
  public void set_new_minefield(int [][] fields ) throws IOException
  {
    gui.Start_game_screen(fields);
  }
  
	/**ACTION METHODS
	 * @throws IOException **/
	
  public void click_happend(Point p, int mouse_event_num) throws IOException
	{
		switch(mouse_event_num){
		case Defines.mouse_left_Pressed: model.LeftClick(p.x, p.y); set_new_minefield(model.getBoard()); break;
		case Defines.mouse_left_Released: break;
		case Defines.mouse_right_Pressed: model.RightClick(p.x, p.y); set_new_minefield(model.getBoard()); break;
		case Defines.mouse_right_Released: break;
		case Defines.mouse_middle_Pressed: model.MiddleClickPushed(p.x, p.y); set_new_minefield(model.getBoard()); break;
		case Defines.mouse_middle_Released: model.MiddleClickReleased(p.x, p.y); set_new_minefield(model.getBoard()); break;
		default: break;
		}
		++clickNo;
		GameStart();
		System.out.println( p.x + " " + p.y + " " +mouse_event_num + " cellsleft: " + model.cellsLeft 
		    + " minesNo: " + model.minesNo + " markedNo: " + model.markedNo + " status: " + status );
	}
	
  public boolean GameStart() throws IOException{
    long endTime;
    set_new_minefield(model.getBoard());
    
    if(status == 0){     
       if(model.cellsLeft <= 0){ status = Defines.WON; } 
       if(model.getBombed() > 0){ status = Defines.LOST; }  
    }
    
    endTime = System.nanoTime() - startTime;
      
    if( status == Defines.WON ) {
      JOptionPane.showMessageDialog(null, "You have won, Duke! taratta taratta taratt tararara");
      JOptionPane.showMessageDialog(null, "Full game length: "+(endTime/1000000000)+" seconds.");
    }
    else if (status == Defines.LOST) {
      JOptionPane.showMessageDialog(null, "You died. Horribly. Noob.");
      JOptionPane.showMessageDialog(null, "Full game length: "+(endTime/1000000000)+" seconds.");
    }
    
    if(status == 0) return false;
    else return true;
  }
  
}
