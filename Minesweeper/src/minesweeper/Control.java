package minesweeper;

import javax.swing.JOptionPane;

import java.awt.Point;
import java.io.IOException;


public class Control {  
  
//  public int status = 0;
  public long startTime;
  public long penaltyStartTime;
  public int clickNo=0;
  
  private Network net = null;
  
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
	
  public void Click_happend(Point p, int mouse_event_num ) throws IOException 
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
		GameStart(2);
		System.out.println( p.x + " " + p.y + " " +mouse_event_num + " cellsleft: " + model.cellsLeft 
		    + " minesNo: " + model.minesNo + " markedNo: " + model.markedNo + " status: " + status );
	}
	
  // server elinditja a jatekot a megfelelo tablamerettel
  public boolean GameStart(int tableSize) throws IOException{ 
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
      JOptionPane.showMessageDialog(null, "You died. Horribly.");
      JOptionPane.showMessageDialog(null, "Full game length: "+(endTime/1000000000)+" seconds.");
    }
    
    if(status == 0) return false;
    else return true;
  }
  
  public void startServer(String name) {
  	if (net != null)
  		net.disconnect();
      net = new Server(this);
      net.connect("localhost", name);
  }

  public void startClient(String name) {
  	if (net != null)
  		net.disconnect();
  	net = new Client(this);
      net.connect("localhost", name);
  }
  
  public void clientReceived(clientsList clientList){
  	clientList.printClientList();
  	//net.send(clientList.copy());
  }
  
  
  
  
  
  
  
  
  
  // kinyomjuk a guira a jelenlevok listajat
  public void clientListPrint(clientsList list){
//    gui.print_list(list);
  }
  
  // kliens kuldi, "keszen allok a jatekra, ezt a tablat szeretnem(0:egyiksem)"
  public void acceptGame(int tableSize){
//    client.send(tableSize);
  }
  
  // elso screenen kliens beallitja a nevet es elkuldi a servernek 
  public void setMyName(String s){
//    client.send(s);
  }
  
  //kliens kuld click eventet a servernek
  public void sendClick(ClickEvent click){
//    client.send(click);
  }
  
  //Server megkapja a klikket, meghivja a game/et
  //clickEvent ben point, mauseevent, myname
  public void receiveClick(ClickEvent click){
    //  Click_happend();
  }
  
  // server kuld tablat klienseknek
  public void sendGameInfo(GameInfo g){
//    server.send(g);
  }
  
  // tabla fogadasa kliensen
  public void receiveGameInfo(GameInfo g){
    //set_new_minefield(g.board);
  }
  
  // mp-enkent a server kuld idot az osszes kliensnek
  public void sendGameTime(TimeStamp t){
//    server.send(t);
  }
  
  public void receiveGameTime(TimeStamp t){
//    gui.setNewTime(t);
  }
  
  // jatek vegen broadcastoljuk a klienseknek a score tablazatot
  public void sendScores(Scores scoreTable){
//    server.send(scoreTable);
  }
  
//  kliensek kirjak a highscore tablazatot
  public void receiveScores(Scores scoreTable){
//    gui.showScores(scoreTable);
  }
  
  
}
