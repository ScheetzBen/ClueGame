package clueGame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {
	// Variables for Board make a grid an empty list for target and visited cells
		private BoardCell[][] grid;
		private int numRows, numColumns;
		private String layoutConfigFile, setupConfigFile;
		private Map<Character, Room> roomMap = new HashMap<Character, Room>();
		
		private static Board theInstance = new Board(); 
		
		private Set<BoardCell> targets = new HashSet<BoardCell>();
		private Set<BoardCell> visited = new HashSet<BoardCell>();
		
		// Constructor for Board creates new BoardCells for all spots in the grid
		// Also calls setAdjacencies for all cells in the grid
		private Board() {
			super();
		}
		
		public static Board getInstance() {
			return theInstance;
		}
		
		public void loadSetupConfig() {
			
		}
		
		public void loadLayoutConfig() {
			
		}
		
		public void setConfigFiles(String layoutConfigFile, String setupConfigFile) {
			
		}
		
		public Room getRoom(BoardCell cell) {
			// return roomMap.get(room);
			return new Room();
		}
		
		public Room getRoom(Character room) {
			// return roomMap.get(room);
			return new Room();
		}
		
		public void initialize() {
			// Code for initializing cells in board
//			grid = new BoardCell[numRows][numColumns];
//			for (int i = 0; i < grid.length; i++) {
//				for(int j = 0; j < grid[i].length; j++) {
//					grid[i][j] = new BoardCell(i, j);
//				}
//			}
//			
//			for (int i = 0; i < grid.length; i++) {
//				for(int j = 0; j < grid[i].length; j++) {
//					grid[i][j].setAdjacencies(this);
//				}
//			}
			
			// code for when we need to 
//			try {
//				FileReader reader = new FileReader("ClueLayout.csv");
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//			
//			Scanner in = new Scanner("ClueLayout.csv");
//			in.useDelimiter(",");
//			
//			while (in.hasNext()) {
//				int currRow = 0, currColumn = 0;
//				BoardCell currCell = new BoardCell(currRow, currColumn);
//				if (in.next() == "X" || in.next() == "W") board.add(currCell);
//				
//			}
		}
		
		// Calculates all possible targets for the start cell given the pathlength
//		public void calcTargets(BoardCell startCell, int pathlength) {
//			visited.add(startCell);
//			
//			Set<BoardCell> temp = startCell.getAdjList();
//			
//			for (BoardCell j : temp) {
//				boolean test = true;
//				for (BoardCell i : visited) {
//					if (i.equals(j)) { 
//						test = false;
//						break;
//					}
//				}
//				if (test && !(j.isOccupied())) {
//					visited.add(j);
//					if (j.isRoom()) {
//						targets.add(j);
//						visited.remove(j);
//						continue;
//					}
//					if (pathlength == 1) targets.add(j);
//					else calcTargets(j, pathlength - 1);
//					visited.remove(j);
//				}
//			}
//		}
//		
		public int getNumRows() {
			return numRows;
		}

		public int getNumColumns() {
			return numColumns;
		}

		// Resets the targets and visited sets and returns the targets
		public Set<BoardCell> getTargets() {
			Set<BoardCell> temp = targets;
			targets = new HashSet<BoardCell>();
			visited = new HashSet<BoardCell>();
			return temp;
		}
		
		// Return a cell in the grid
		public BoardCell getCell(int row, int col) {
//			return grid[row][col];
			return new BoardCell(0,0);
		}
}
