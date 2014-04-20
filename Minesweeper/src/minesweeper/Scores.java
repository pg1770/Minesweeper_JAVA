package minesweeper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Scores implements Serializable{
    private static final long serialVersionUID = 20140411;
    
//    List<Object> scoreList = new ArrayList<Object>();
    private List<Integer> scoreList = new ArrayList<Integer>();
    private List<String> nameList = new ArrayList<String>(); 
    
    public void addScoreToUser(int scoreToAdd, String toUser){
      int index = -1;
      for(String s: nameList){
        ++index;
        if( s.trim().equals(toUser.trim()) ){
          scoreList.set(index, scoreList.get(index) + scoreToAdd);
        }
      }       
    }
    
    public void addUser(String user){
      nameList.add(user);
      scoreList.add(0);   // with zero score
    }
    
    public List<Integer> getScores(){
      return scoreList;
    }
    
    public List<String> getNames(){
      return nameList;
    }
}
