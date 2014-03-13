package minesweeper;

import javax.swing.JOptionPane;

import java.awt.Point;
import java.io.IOException;


public class Control {  
  
  public int status = 0;
  
  public Model model;
  
	GUI gui;
	
	/**CONSTRUCTORS**/
	
	public Control(int width, int height, int minesNo){
    model = new Model(width, height, minesNo);
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
		
		System.out.println(p.x +" " +p.y + mouse_event_num);
	}
	
  public void GameStart() throws IOException{
    long timer = System.currentTimeMillis();
    set_new_minefield(model.getBoard());
    
    while(status == 0){
      
       
       //mouse eventek

      //Debug(model);
      
       if(model.cellsLeft <= 0){ status = Defines.WON; } //lehetne esetleg UNKNOWN-okat is szamolni
         
       // ha lastcell(ek) mine-ok, azt is kezelni?
    }
    
    long endTime = System.currentTimeMillis() - timer;
      
   
    if( status == Defines.WON ) JOptionPane.showMessageDialog(null, "You have won, Duke! taratta taratta taratt tararara");
    else JOptionPane.showMessageDialog(null, "You died. Horribly. Noob.");
    JOptionPane.showMessageDialog(null, "Full game length: "+(endTime/1000)+" seconds.");
  }
  
//  public void print(Model m){
//    for( int i = 0; i < m.width; ++i ){
//      for( int j = 0; j < m.height; ++j ){
//        if(m.board[i][j] < 9) System.out.print(m.board[i][j]);
//        else switch(m.board[i][j]){
//        case 10: System.out.print("."); break;
//        case 12: System.out.print("$"); break;
//        case 13: System.out.print("?"); break;
//        case 15: System.out.print("$"); break;
//        }
//      }
//      System.out.println();
//    }
//    for( int i = 0; i < m.height; ++i )      
//      System.out.print(" ");
//    System.out.println();
//  }
//  
//  public void Debug(Model m){
//    print(m);
//    m.LeftClick(1,2); print(m);
//    m.RightClick(5, 6); print(m);
//    m.LeftClick(8, 8); print(m);
//    m.MiddleClickPushed(9, 9); print(m);
//    m.MiddleClickReleased(9, 9); print(m);
//    if(m.bombed > 0) status = 2;
//  }

	//timer
	//winflagek
	//no of moves
  
}
