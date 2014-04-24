package minesweeper;

import java.util.Random;

public class Model extends GameInfo{
    private static final long serialVersionUID = 20140408;
		
    protected int width;
		protected int height;
		protected int minesNo;
		protected int cellsLeft;
		protected int markedNo;
		protected boolean [][] mines;
		protected String penaltyUser;
		protected long penaltyStart;

		/**CONSTRUCTOR**/
		
		public Model(int width, int height, int minesNo){
			mines = new boolean [width][height];
			board = new int [width][height];		
			this.width = width;
			this.height = height;
			
			ModelClear();
			
			this.minesNo = minesNo;
	
			PlaceMines();
		}
		
		/**ACCESS METHODS**/
		
    public int [][] getBoard(){return board;}
		
		/**ACTION METHODS**/
		
		private void ModelClear(){
			for(int i = 0; i < width; ++i)
				for(int j = 0; j < height; ++j){
					mines[i][j] = false;
					board[i][j] = Defines.UNKNOWN;
				}
			cellsLeft = width * height;
			markedNo = 0;
			minesNo = 0;
		}
		
		private void PlaceMines(){
			Random random = new Random();
			int w;
			int h;
			for(int i = 0; i < minesNo; ++i){
			  w = random.nextInt(width-1);
        h = random.nextInt(height-1);
			  while(mines[w][h] != false){
			    w = random.nextInt(width-1);
			    h = random.nextInt(height-1);
			  }
				mines[w][h] = true;
			}
		}
		
		public void OopsBomb(String user){
		  System.out.println("Felpukkantal" + user + ".");
		  if(minesNo < 5) addScoreToUser(-10, user);  
      else addScoreToUser(-2, user);
		  penaltyUser = user;
		  penaltyStart = System.nanoTime();
		  --cellsLeft;
		  // Control ClickReceived-ben folyt
		  }
		
		private int MinesAround(int x, int y){
		  int minesAround = 0;
		  for(int i = (x == 0 ? 0 : x-1); i <= (x == width-1 ? width-1 : x+1); ++i )
		    for(int j = (y == 0 ? 0 : y-1); j <= (y == height-1 ? height-1 : y+1); ++j )
		      if( (mines[i][j] == true) && ( !(i == x && j == y) ) ) ++minesAround;
		  return minesAround;
		}
		
		
		private void EmptyShowMore(int x, int y, String user){  //hivni akkor, ha sqvalue == 0
		  for(int i = (x == 0 ? 0 : x-1); i <= (x == width-1 ? width-1 : x+1); ++i )
        for(int j = (y == 0 ? 0 : y-1); j <= (y == height-1 ? height-1 : y+1); ++j )
          if( (mines[i][j] != true) && (board[i][j] == Defines.UNKNOWN) ){
            LeftClick(i,j, user);                   //ebben mar van --cellsLeft; 
            if(board[i][j] == 0)
              EmptyShowMore(i,j, user);
          }       
    }
		
		private void MineMark(int x, int y, String user){ //ha FLAGGED-re hivjuk, MARKED lehet
		  if(board[x][y] == Defines.FLAGGED && mines[x][y] == true){
		    board[x][y] = Defines.MARKED;
		    if(minesNo < 5) addScoreToUser(5, user); 
		    else addScoreToUser(1, user);
		    --minesNo;
		    ++markedNo;
		    --cellsLeft;
		  } 
		    
		  else if(board[x][y] == Defines.QMARKED && mines[x][y] == true){ //ha MARKED-ot unflagelunk
      if(minesNo < 5) addScoreToUser(-5, user);  
      else addScoreToUser(-1, user);		    
		    ++minesNo;
        --markedNo;
        ++cellsLeft;
		  }
		  
		}
		
		public void LeftClick(int x, int y, String user){
			switch(board[x][y]){
			case Defines.EXPLODED:
			case Defines.FLAGGED:
			case Defines.MARKED: ; break;
			case 0: ; break; case 1: ; break; case 2: ; break; case 3: ; break; case 4: ; break; case 5: ; break;
			case 6: ; break; case 7: ; break; case 8: ; break; case 9: ; break;
			case Defines.QMARKED:
			case Defines.UNKNOWN: 
			  if(mines[x][y] == true) {board[x][y] = Defines.EXPLODED; OopsBomb(user);}
			  else {
          board[x][y] = MinesAround(x,y);
          --cellsLeft;
          if(board[x][y] == 0) EmptyShowMore(x, y, user);			 
			} break; 
			default: System.out.println("LeftClick Error"); break;
			}
		}
		
		public void RightClick(int x, int y, String user){ 
		  switch(board[x][y]){
		  case Defines.FLAGGED: {
		    board[x][y] = Defines.QMARKED;
		  //  MineMark(x, y);
		    break; 
		  }
		  case Defines.MARKED: {
        board[x][y] = Defines.QMARKED; 
        MineMark(x, y, user); //ebben mar van --cellsLeft;
        break; 
      }
		  case Defines.QMARKED: board[x][y] = Defines.UNKNOWN; break;
		  case Defines.UNKNOWN: {
		    board[x][y] = Defines.FLAGGED; 
		    MineMark(x, y, user);
		    break; 
		  }
		  case 0: ; break; case 1: ; break; case 2: ; break; case 3: ; break; case 4: ; break; case 5: ; break;
      case 6: ; break; case 7: ; break; case 8: ; break; case 9: ; break;
		  default: System.out.println("RightClick Error"); break;
		  }
		}
		
		public void MiddleClickPushed(int x, int y){
		  for(int i = (x == 0 ? 0 : x-1); i <= (x == width-1 ? width-1 : x+1); ++i )
        for(int j = (y == 0 ? 0 : y-1); j <= (y == height-1 ? height-1 : y+1); ++j )
          switch(board[i][j]){
          case Defines.EXPLODED:
          case Defines.FLAGGED: 
          case Defines.MARKED: ; break;
          case 0: ; break; case 1: ; break; case 2: ; break; case 3: ; break; case 4: ; break; case 5: ; break;
          case 6: ; break; case 7: ; break; case 8: ; break; case 9: ; break;
          case Defines.QMARKED: board[i][j] += Defines.PUSHED; break;
          case Defines.UNKNOWN: board[i][j] += Defines.PUSHED; break;
          default: System.out.println("MiddleClickPushed Error"); break;
          }
		}
		
		public void MiddleClickReleased(int x, int y, String user){  //same x and y as in last MiddleClickPushed(x,y)!!
		  int flaggedNMarked = 0;
      for(int i = (x == 0 ? 0 : x-1); i <= (x == width-1 ? width-1 : x+1); ++i )
        for(int j = (y == 0 ? 0 : y-1); j <= (y == height-1 ? height-1 : y+1); ++j ){
          if( board[i][j] >= Defines.PUSHED ) board[i][j] -= Defines.PUSHED;
          if( ( board[i][j] == Defines.MARKED || board[i][j] == Defines.FLAGGED ) && ( (i != x) || (j != y) ) ) 
            ++flaggedNMarked;
        }
      if( MinesAround(x, y) == flaggedNMarked && board[x][y] <= 9 ){
        for(int i = (x == 0 ? 0 : x-1); i <= (x == width-1 ? width-1 : x+1); ++i )
          for(int j = (y == 0 ? 0 : y-1); j <= (y == height-1 ? height-1 : y+1); ++j )
            switch(board[i][j]){
            case Defines.FLAGGED: ; break;
            case Defines.MARKED: ; break;
            case 0: ; break; case 1: ; break; case 2: ; break; case 3: ; break; case 4: ; break; case 5: ; break;
            case 6: ; break; case 7: ; break; case 8: ; break; case 9: ; break;
            case Defines.QMARKED: LeftClick(i, j, user); break;
            case Defines.UNKNOWN: LeftClick(i, j, user); break;
            default: System.out.println("MiddleClickPushed Error"); break;
            }
      }
		}
		
		// TODO: idobuntetes, MiddleClickhiba
		// ehhez is kell USER, ezt lehetne ugy, hogy bombed ad vissza usert is, broadcastnal
		// az kellene, h adott user  clickeventjeit meg letiltjuk, meg kene neki egy messagebox
		// kozepso mar mine felfedjen
}