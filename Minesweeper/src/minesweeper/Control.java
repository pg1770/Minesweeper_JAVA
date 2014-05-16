package minesweeper;

import javax.swing.JOptionPane;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class Control{  
  
  private final int penaltySec = 2;
  
  private Server server = null;
  
  private Client client = null;
  
  private Model model;
  
	GUI gui;
	
	/**CONSTRUCTORS**/
	
	public Control(){}
	
	/**ACCESS METHODS**/
	
  public void setGUI(GUI gui)
  {
    this.gui = gui;
  }
  
	/**ACTION METHODS
	 * @throws IOException **/
	
  // server elinditja a jatekot a megfelelo tablamerettel
  public boolean GameStart(int tableSize) throws IOException{
    switch(tableSize){
    case 1: model = new Model(10, 10, 20); break; //Model(width, height, minesNo);
    case 2: model = new Model(20, 20, 80); break;
    case 3: model = new Model(50, 30, 300); break;
    default: JOptionPane.showMessageDialog(null, "Tablesize error!"); break;
    }
    GameInfo gameInfoTemp = new GameInfo();
    gameInfoTemp.board =  model.getBoard();
    sendGameInfo(gameInfoTemp);
  
    final long startT = System.nanoTime();
    
    new Timer().schedule(new TimerTask() {
      public void run()  {            
        TimeStamp tempTS = new TimeStamp();
        tempTS.setStartTime(startT);
        tempTS.setTimeElapsed(Math.round(- (startT - System.nanoTime()) /1000000000));
        sendGameTime(tempTS);
      }
      }, 1, 1000);
    
    return false;
  }
  
  public void startServer() {
  	if (server != null)
  		server.disconnect();
      server = new Server(this);
      server.connect();
  }

  public void startClient() {
  	if (client != null)
  		client.disconnect();
  	  client = new Client(this);
      client.connect();
  }
  
  void playersListPrint(PlayersList players){
      gui.printList(players);
  }

  // kliens kuldi, "keszen allok a jatekra, ezt a tablat szeretnem(0:egyiksem)"
  public void acceptGame(int tableSize){
    client.send(tableSize);
  }
  
  // elso screenen kliens beallitja a nevet es elkuldi a servernek 
  public void setMyName(String s){
    client.send(s);
  }
  
  //kliens kuld click eventet a servernek
  public void sendClick(ClickEvent click){
    client.send(click);
  }
  
  //Server megkapja a klikket, meghivja a game-et
  //clickEventben point, mouseevent, myname
  public void receiveClick(ClickEvent click) throws IOException { 
    GameInfo gameInfoTemp = new GameInfo();
    if ( ( (System.nanoTime() - model.penaltyStart) / 1000000000 ) >= penaltySec ) model.penaltyUser = null;
    if ( !click.myname.equals(model.penaltyUser) ){
      switch(click.mouse_event_num){
      case Defines.mouse_left_Pressed: model.LeftClick(click.p.x, click.p.y, click.myname); gameInfoTemp.board =  model.getBoard(); sendGameInfo(gameInfoTemp); break;
      case Defines.mouse_left_Released: break;
      case Defines.mouse_right_Pressed: model.RightClick(click.p.x, click.p.y, click.myname); gameInfoTemp.board =  model.getBoard(); sendGameInfo(gameInfoTemp); break;
      case Defines.mouse_right_Released: break;
      case Defines.mouse_middle_Pressed: model.MiddleClickPushed(click.p.x, click.p.y); gameInfoTemp.board =  model.getBoard(); sendGameInfo(gameInfoTemp); break;
      case Defines.mouse_middle_Released: model.MiddleClickReleased(click.p.x, click.p.y, click.myname); gameInfoTemp.board =  model.getBoard(); sendGameInfo(gameInfoTemp); break;
      default: break;
      }
    }
    
    Scores scoreTemp = new Scores();
    scoreTemp.setNames(model.getNames()); 
    scoreTemp.setScores(model.getScores());
    if(model.cellsLeft <= 0){ sendScores(scoreTemp); }
  }
  
  // server kuld tablat klienseknek
  public void sendGameInfo(GameInfo g){
    server.broadcast(g);
  }
  
  // tabla fogadasa kliensen
  public void receiveGameInfo(GameInfo g) throws IOException, InterruptedException{
	  gui.SetGameScreen(g.board);
  }
  
  // mp-enkent a server kuld idot az osszes kliensnek
  public void sendGameTime(TimeStamp t){  
    server.broadcast(t);
  }
  
  public void receiveGameTime(TimeStamp t) throws IOException{
    gui.setNewTime(t);
  }
  
  // jatek vegen broadcastoljuk a klienseknek a score tablazatot
  public void sendScores(Scores scoreTable){
    server.broadcast(scoreTable);
  }
  
//  kliensek kirjak a highscore tablazatot
  public void receiveScores(Scores scoreTable){
    try {
      gui.showScores(scoreTable);
    } catch (IOException e) {
		e.printStackTrace();
    }
  }
  
  void clientError(String error){
	 JOptionPane.showMessageDialog(null, error);
  }
  
  void serverError(String error){
	 JOptionPane.showMessageDialog(null, error);
  }
  
  void printServerMessage(String msg)
  {
	  gui.printServerLog(msg);
  }
  
}
