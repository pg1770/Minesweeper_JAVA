package minesweeper;

import javax.swing.JOptionPane;

import java.awt.Point;
import java.io.IOException;


public class Control {  
  
  public int status = 0;
  
  private Model model;
  
	GUI gui;
	
	/**CONSTRUCTORS**/
	
	public Control(int width, int height, int minesNo){
    model = new Model(width, height, minesNo);
  }
	
	public Control(){}
	
	/**ACTION METHODS**/
	
	public void setGUI(GUI gui)
	{
		this.gui = gui;
	}
	
	public void set_new_minefield(int x, int y) throws IOException
	{
		//gui.set_new_Size(x, y);
	}
	
	public void click_happend(Point p)
	{
		System.out.println(p.x +" " +p.y);
	}
	
 public void GameStart(){
    long timer = System.currentTimeMillis();
    
    while(status == 0){
       //draw()
       
       //mouse eventek
      Debug(model);
      
       if(model.cellsLeft <= 0){ //lehetne esetleg UNKNOWN-okat is szamolni
         status = Defines.WON;
       }
       // ha lastcell(ek) mine-ok azt is kezelni
     }
    
    long endTime = System.currentTimeMillis() - timer;
      
   
    if( status == Defines.WON ) JOptionPane.showMessageDialog(null, "You have won, Duke! taratta taratta taratt tararara");
    else JOptionPane.showMessageDialog(null, "You died. Horribly. Noob.");
    JOptionPane.showMessageDialog(null, "Full game length: "+(endTime/1000)+" seconds.");
  }
  
  public void print(Model m){
    for( int i = 0; i < m.width; ++i ){
      for( int j = 0; j < m.height; ++j ){
        if(m.board[i][j] < 9) System.out.print(m.board[i][j]);
        else switch(m.board[i][j]){
        case 10: System.out.print("."); break;
        case 12: System.out.print("$"); break;
        case 13: System.out.print("?"); break;
        case 15: System.out.print("$"); break;
        }
      }
      System.out.println();
    }
    for( int i = 0; i < m.height; ++i )      
      System.out.print(" ");
    System.out.println();
  }
  
  public void Debug(Model m){
    print(m);
    m.LeftClick(1,2); print(m);
    m.RightClick(5, 6); print(m);
    m.LeftClick(8, 8); print(m);
    m.MiddleClickPushed(9, 9); print(m);
    m.MiddleClickReleased(9, 9); print(m);
    if(m.bombed > 0) status = 2;
  }

	//timer
	//winflagek
	//no of moves
}
