package minesweeper;

import javax.swing.JOptionPane;

public class Game {
  public static final int WON = 1;
  public static final int LOST = 2;
  
  public int status;
  
  private Model model;
  
  public Game(int width, int height, int minesNo){
    model = new Model(width, height, minesNo);
  }
  
  public void GameStart(){
    long timer = System.currentTimeMillis();
    
    while(status != 1){
       //draw()
      
       //mouse eventek
      Debug(model);
      
       if(model.cellsLeft <= 0){ //lehetne esetleg UNKNOWN-okat is szamolni
         status = WON;
       }
       // ha lastcell(ek) mine-ok azt is kezelni
     }
    
    long endTime = System.currentTimeMillis() - timer;
  
  //be valami fv-be
//  for(int i = 0; i < width; ++i)
//    for(int j = 0; j < height; ++j){}
//     // model.reveal();
    
    //model.draw();
    
    if( status == WON ) JOptionPane.showMessageDialog(null, "You have won, Duke! taratta taratta taratt tararara");
    else JOptionPane.showMessageDialog(null, "You died. Horribly. Noob.");
    JOptionPane.showMessageDialog(null, "Full game length: "+(endTime/1000)+" seconds.");
  }
  
  public void Debug(Model m){
    m.LeftClick(1,2);
    m.RightClick(5, 6);
    m.LeftClick(8, 8);
    m.MiddleClickPushed(9, 9);
    m.MiddleClickReleased(9, 9);
  }
}


