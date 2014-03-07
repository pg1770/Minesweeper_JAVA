package minesweeper;

import java.util.Random;


public class Model {
  
    /**ENUMLIKE**/
  
		public static final int UNKNOWN = 10;
//		public static final int REVEALED = 11;
		public static final int FLAGGED = 12;
		public static final int QMARKED = 13;
		public static final int EXPLODED = 14;
		public static final int MARKED = 15;
		public static final int PUSHED = 16;
		
	  /**DATA**/
		
		protected int width;
		protected int height;
		protected int minesNo;
		protected int cellsLeft;
		protected int markedNo;
		protected boolean [][] mines;
		protected int [][] board;
		
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
		
		/**ACTION METHODS**/
		
		private void ModelClear(){
			for(int i = 0; i < width; ++i)
				for(int j = 0; j < height; ++j){
					mines[i][j] = false;
					board[i][j] = UNKNOWN;
				}
			cellsLeft = width * height;
			markedNo = 0;
			minesNo = 0;
		}
		
		private void PlaceMines(){
			Random random = new Random();
			for(int i = 0; i < minesNo; ++i)
				mines[random.nextInt(width-1)][random.nextInt(height-1)] = true;
		}
		
		public int [][] getBoard(){return board;}
		
		//draw()
		
		public void OopsBomb(){
		  System.out.println("Vesztettel!!");
		  
		  }
		
		public int MinesAround(int x, int y){
		  int minesAround = 0;
		  for(int i = (x == 0 ? 0 : x-1); i <= (x == width-1 ? width-1 : x+1); ++i )
		    for(int j = (y == 0 ? 0 : y-1); j <= (y == height-1 ? height-1 : y+1); ++j )
		      if( (mines[i][j] == true) && ( (i != x) && (j != y) ) ) ++minesAround;
		  return minesAround;
		}
		// fix bombakkal kene debuggolni
		public void EmptyShowMore(int x, int y){  //hivni akkor, ha sqvalue == 0
		  for(int i = (x == 0 ? 0 : x-1); i <= (x == width-1 ? width-1 : x+1); ++i )
        for(int j = (y == 0 ? 0 : y-1); j <= (y == height-1 ? height-1 : y+1); ++j )
          if( mines[i][j] != true && board[i][j] == UNKNOWN && (x == i || y == j)){
            LeftClick(i,j);                   //ebben mar van --cellsLeft; 
            if(board[i][j] == 0)
              EmptyShowMore(i,j);
          }       
    }
		
		public void MineMark(int x, int y){ //ha FLAGGED-re hvjuk MARKED lehet
		  if(board[x][y] == FLAGGED && mines[x][y] == true){
		    board[x][y] = MARKED;
		    --minesNo;
		    ++markedNo;
		    --cellsLeft;
		  } 
		    
		  if(board[x][y] != FLAGGED && board[x][y] == MARKED){ //ha MARKED-ot unflagelunk
		    board[x][y] = FLAGGED;
		    ++minesNo;
        --markedNo;
        ++cellsLeft;
		  }
		  
		}
		
		public void LeftClick(int x, int y){
			switch(board[x][y]){
			case FLAGGED: ; break;
			case MARKED: ; break;
			case 0: ; break; case 1: ; break; case 2: ; break; case 3: ; break; case 4: ; break; case 5: ; break;
			case 6: ; break; case 7: ; break; case 8: ; break; case 9: ; break;
			case QMARKED: {
        if(mines[x][y] == true) {board[x][y] = EXPLODED; OopsBomb();}
        else {
          board[x][y] = MinesAround(x,y);
          --cellsLeft;
          if(board[x][y] == 0) EmptyShowMore(x, y);
        }
      } break;
			case UNKNOWN: {
			  if(mines[x][y] == true) {board[x][y] = EXPLODED; OopsBomb();}
			  else {
          board[x][y] = MinesAround(x,y);
          --cellsLeft;
          if(board[x][y] == 0) EmptyShowMore(x, y);
        }
			  break;
			} 
			default: System.out.println("LeftClick Error"); break;
			}
		}
		
		public void RightClick(int x, int y){ 
		  switch(board[x][y]){
		  case FLAGGED: {
		    board[x][y] = QMARKED;
		  //  MineMark(x, y);
		    break; 
		  }
		  case MARKED: {
        board[x][y] = QMARKED; 
        MineMark(x, y); //ebben mar van --cellsLeft;
        break; 
      }
		  case QMARKED: board[x][y] = UNKNOWN; break;
		  case UNKNOWN: {
		    board[x][y] = FLAGGED; 
		    MineMark(x, y);
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
          case FLAGGED: ; break;
          case MARKED: ; break;
          case 0: ; break; case 1: ; break; case 2: ; break; case 3: ; break; case 4: ; break; case 5: ; break;
          case 6: ; break; case 7: ; break; case 8: ; break; case 9: ; break;
          case QMARKED: board[i][j] += PUSHED; break;
          case UNKNOWN: board[i][j] += PUSHED; break;
          default: System.out.println("MiddleClickPushed Error"); break;
          }
		}
		
		public void MiddleClickReleased(int x, int y){  //same x and y as in MiddleClickPushed(x,y)!!
		  int flaggedNMarked = 0;
      for(int i = (x == 0 ? 0 : x-1); i <= (x == width-1 ? width-1 : x+1); ++i )
        for(int j = (y == 0 ? 0 : y-1); j <= (y == height-1 ? height-1 : y+1); ++j ){
          if( board[i][j] >= PUSHED ) board[i][j] -= PUSHED;
          if( ( board[i][j] == MARKED || board[i][j] == FLAGGED ) && ( (i != x) && (j != y) ) ) ++flaggedNMarked;
        }
      if( MinesAround(x, y) == flaggedNMarked && board[x][y] <= 9 ){
        for(int i = (x == 0 ? 0 : x-1); i <= (x == width-1 ? width-1 : x+1); ++i )
          for(int j = (y == 0 ? 0 : y-1); j <= (y == height-1 ? height-1 : y+1); ++j )
            switch(board[i][j]){
            case FLAGGED: ; break;
            case MARKED: ; break;
            case 0: ; break; case 1: ; break; case 2: ; break; case 3: ; break; case 4: ; break; case 5: ; break;
            case 6: ; break; case 7: ; break; case 8: ; break; case 9: ; break;
            case QMARKED: LeftClick(i, j); break;
            case UNKNOWN: LeftClick(i, j); break;
            default: System.out.println("MiddleClickPushed Error"); break;
            }
      }
		}
				
	}
	
