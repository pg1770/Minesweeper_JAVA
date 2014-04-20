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
   
   public void setTimeElapsed(double time){
     timeElapsed = time;
     }
   
   public double getTimeElapsed(){ //nanosec
     return timeElapsed;
   }
  
   @Override
   public void run() {
    timeElapsed = startTime - System.nanoTime();
    // TODO: Display on GUI
    
   }
   
 }
