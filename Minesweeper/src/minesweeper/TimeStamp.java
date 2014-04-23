  package minesweeper;
  
 import java.io.Serializable;
 import java.util.TimerTask;
  
 public class TimeStamp extends TimerTask implements Serializable{
   private static final long serialVersionUID = 20140410;
    
   public double timeElapsed;
   public double startTime;
   
   public TimeStamp(){
     startTime = System.nanoTime();
   }
   
   public void setTimeElapsedSecond(int sec){
	   timeElapsed = sec*1000000;
   }
   
   public void setTimeElapsed(double time){
     timeElapsed = time;
     }
   
   public void setStartTime(double time){
     startTime = time;
     }
   
   public int getTimeElapsedSecond(){
	   return (int)(timeElapsed/1000000);
   }
   
   public double getTimeElapsed(){
     return timeElapsed;
   }
  
   @Override
   public void run() {
    timeElapsed = startTime - System.nanoTime();
    // TODO: Display on GUI
//  SendGameTime();
    System.out.println("TimeElapsed: " + timeElapsed/1000000000);
   }
   
 }
