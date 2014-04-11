package minesweeper;

import java.io.Serializable;

public class GameInfo extends Scores implements Serializable {
  private static final long serialVersionUID = 20140322;
  
  protected int [][] board;
}
