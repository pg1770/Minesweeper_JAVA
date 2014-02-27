package minesweeper;

import java.util.Random;


public class Model {
	
	public class SquareUI{
		public static final int UNKNOWN = 0;
		public static final int REVEALED = 1;
		public static final int FLAGGED = 2;
		public static final int QMARKED = 3;

		public int uiStatus;
	}
	
	public class SquareLogic{
		public static final int MINE = 9;
		public static final int MARKED = 10;
		
		public int sqValue; 
	}
	
	public abstract class Board{
		protected int width;
		protected int height;
		protected int minesNo;
		protected int cellsLeft;
		protected int markedNo;
//		protected boolean [][] minesPlace;
		protected SquareLogic [][] logicBoard;
		protected SquareUI [][] uiBoard;
		
		/**CONSTRUCTOR**/
		
		public Board(int width, int height, int minesNo){
//			minesPlace = new boolean[width][height];
			logicBoard = new SquareLogic [width][height];
			uiBoard = new SquareUI [width][height];
			
			BoardClear();
			
			this.width = width;
			this.height = height;
			this.minesNo = minesNo;
			
			PlaceMines();
		}
		
		/**ACTION METHODS**/
		
		private void BoardClear(){
			for(int i = 0; i < width; ++i)
				for(int j = 0; j < height; ++j){
//					minesPlace[i][j] = false;
					logicBoard[i][j].sqValue = 0;
					uiBoard[i][j].uiStatus = SquareUI.UNKNOWN;
				}
			cellsLeft = width * height;
			markedNo = 0;
			minesNo = 0;
		}
		
		private void PlaceMines(){
			Random random = new Random();
			for(int i = 0; i < minesNo; ++i)
				logicBoard[random.nextInt(width-1)][random.nextInt(height-1)].sqValue = SquareLogic.MINE;
		}
		
		public abstract void Draw();
		public void Yolo(){};
		public int MinesAround(int x, int y){
		  
		  return 0;
		}
		
		public void LeftClick(int x, int y){
			switch(uiBoard[x][y].uiStatus){
				case SquareUI.FLAGGED: uiBoard[x][y].uiStatus = SquareUI.UNKNOWN; break;
			  case SquareUI.QMARKED: uiBoard[x][y].uiStatus = SquareUI.UNKNOWN; break;
				case SquareUI.REVEALED: ;
				case SquareUI.UNKNOWN: {
				  --cellsLeft;
				  if(logicBoard[x][y].sqValue == SquareLogic.MINE) Yolo();
				  else logicBoard[x][y].sqValue = MinesAround(x,y);
				}
				default: System.out.println("Error");
			}
			
		} 
	}
	
	
	
	
}
