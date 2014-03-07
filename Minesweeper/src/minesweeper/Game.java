package minesweeper;

import javax.swing.JOptionPane;

public class Game {
  public static final int WON = 1;
  public static final int LOST = 2;
  
  public int status;
  
  private Model model; //itt fos valami
  
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
  }
}


