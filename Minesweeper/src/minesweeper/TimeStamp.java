  package minesweeper;
  
 import java.io.Serializable;
  
 public class TimeStamp implements Serializable{
   private static final long serialVersionUID = 20140410;
    
   public double timeElapsed;
   public double startTime;
   
   public TimeStamp(){
     startTime = System.nanoTime();
     timeElapsed = startTime;
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
	   return (int)(timeElapsed);
   }
   
   public double getTimeElapsed(){
     return timeElapsed;
   }
   
 }
