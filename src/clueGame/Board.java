package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

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
		
		// Returns the one Board instance
		public static Board getInstance() {
			return theInstance;
		}
		
		// Calls loadSetupConfig and loadLayoutConfig and catches their exceptions
		public void initialize() {
			try {
				this.loadSetupConfig();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (BadConfigFormatException e) {
				System.out.println(e.getMessage());
			}
			
			try {
				this.loadLayoutConfig();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (BadConfigFormatException e) {
				System.out.println(e.getMessage());
			}
		}
		
		// Properly loads the setup file
		public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException{
			FileReader reader = new FileReader(setupConfigFile);

			Scanner in = new Scanner(reader);

			while (in.hasNext()) {
				String hold = in.nextLine();
				String[] array = hold.split(", ");
				if (array[0].equals("Room") || array[0].equals("Space")) {
					Room room = new Room(array[1]);
					
					String holdLetter = array[2];
					var letter = holdLetter.charAt(0);
					
					roomMap.put(letter, room);
				} else if (hold.charAt(0) == '/') {
					continue;
				} else {
					throw new BadConfigFormatException(setupConfigFile);
				}
			}
			in.close();
		}

		// Properly loads the layout file and sets all BoardCell variables as well as Room centers and labels
		public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {
			FileReader reader = new FileReader(layoutConfigFile);

			Scanner in = new Scanner(reader);
			
			Vector<String[]> rows = new Vector<String[]>();
			
			while (in.hasNext()) {
				String line = in.nextLine();
				
				if (!line.isEmpty()) {
					String[] currCol = line.split(",");
					rows.add(currCol);
				}
			}
			
			numRows = rows.size();
			numColumns = rows.elementAt(0).length;
			
			for (String[] i : rows) {
				if (i.length != numColumns) throw new BadConfigFormatException(layoutConfigFile);
			}
			
			grid = new BoardCell[numRows][numColumns];
			
			for (int i = 0; i < grid.length; i++) {
				for(int j = 0; j < grid[i].length; j++) {
					if (!roomMap.containsKey(rows.elementAt(i)[j].charAt(0))) throw new BadConfigFormatException(layoutConfigFile);
					
					grid[i][j] = new BoardCell(i, j, rows.elementAt(i)[j].charAt(0));
					
					if (rows.elementAt(i)[j].length() > 1) {
						char specialType = rows.elementAt(i)[j].charAt(1);
						BoardCell currCell = this.getCell(i, j);
						switch(specialType) {
							case '*':
								grid[i][j].setCenter(true);
								roomMap.get(currCell.getInitial()).setCenterCell(currCell);
								break;
							case '#':
								grid[i][j].setLabel(true);
								roomMap.get(currCell.getInitial()).setLabelCell(currCell);
								break;
							case '^':
								grid[i][j].setDoorDirection(DoorDirection.UP);
								break;
							case 'v':
								grid[i][j].setDoorDirection(DoorDirection.DOWN);
								break;
							case '<':
								grid[i][j].setDoorDirection(DoorDirection.LEFT);
								break;
							case '>':
								grid[i][j].setDoorDirection(DoorDirection.RIGHT);
								break;
							default:
								grid[i][j].setSecretPassage(specialType);	
						}
					}
				}
			}

			for (int i = 0; i < grid.length; i++) {
				for(int j = 0; j < grid[i].length; j++) {
					grid[i][j].setAdjacencies(this);
				}
			}

		}
		
		// Calculates all possible targets for the start cell given the pathlength
				public void calcTargets(BoardCell startCell, int pathlength) {
//					visited.add(startCell);
//					
//					Set<BoardCell> temp = startCell.getAdjList();
//					
//					for (BoardCell j : temp) {
//						boolean test = true;
//						for (BoardCell i : visited) {
//							if (i.equals(j)) { 
//								test = false;
//								break;
//							}
//						}
//						if (test && !(j.isOccupied())) {
//							visited.add(j);
//							if (j.isRoom()) {
//								targets.add(j);
//								visited.remove(j);
//								continue;
//							}
//							if (pathlength == 1) targets.add(j);
//							else calcTargets(j, pathlength - 1);
//							visited.remove(j);
//						}
//					}
				}
		
		// Getters for board variables
		public void setConfigFiles(String layoutConfigFile, String setupConfigFile) {
			this.layoutConfigFile = new String(layoutConfigFile);
			this.setupConfigFile = new String(setupConfigFile);
		}
		
		public Room getRoom(BoardCell cell) {
			return roomMap.get(cell.getInitial());
		}
		
		public Room getRoom(Character room) {
			return roomMap.get(room);
		}
		
		public int getNumRows() {
			return numRows;
		}

		public int getNumColumns() {
			return numColumns;
		}
		
		// Return a cell in the grid
		public BoardCell getCell(int row, int col) {
			return grid[row][col];
		}
		
		public Set<BoardCell> getAdjList(int row, int col) {
			return this.getCell(row, col).getAdjList();
		}
		
		// Resets the targets and visited sets and returns the targets
		public Set<BoardCell> getTargets() {
//			Set<BoardCell> temp = targets;
//			targets = new HashSet<BoardCell>();
//			visited = new HashSet<BoardCell>();
//			return temp;
			
			return new HashSet<BoardCell>();
		}
}
