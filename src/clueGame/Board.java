package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;

public class Board {
	// Variables for Board
	
	// Initialize a grid array to hold all the cell in the board
	private BoardCell[][] grid;
	
	// variables to hold the number of rows and columns
	private int numRows, numColumns;
	
	// Holds the filenames for the layout and setup config files
	private String layoutConfigFile, setupConfigFile;
	
	// Map to hold all the different characters and their relates Room objects
	private Map<Character, Room> roomMap = new HashMap<Character, Room>();

	// static variable of Board so that there is only ever one Board object created when the program is running
	private static Board theInstance = new Board(); 

	// Sets to hold information for calculating targets
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
			String[] array = in.nextLine().split(", ");
			
			if (array[0].equals("Room") || array[0].equals("Space")) {
				Room.TileType type;

				if (array[0].equals("Room")) type = Room.TileType.ROOM;
				else type = Room.TileType.SPACE;

				roomMap.put(array[2].charAt(0), new Room(array[1], type));

			} else if (array[0].charAt(0) == '/') {
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

		ArrayList<String[]> rows = new ArrayList<String[]>();

		while (in.hasNext()) {
			String line = in.nextLine();

			if (!line.isEmpty()) {
				String[] currCol = line.split(",");
				rows.add(currCol);
			}
		}

		numRows = rows.size();
		numColumns = rows.get(0).length;

		for (String[] i : rows) {
			if (i.length != numColumns) throw new BadConfigFormatException(layoutConfigFile);
		}

		grid = new BoardCell[numRows][numColumns];

		for (int row = 0; row < grid.length; row++) {
			for(int col = 0; col < grid[row].length; col++) {
				if (!roomMap.containsKey(rows.get(row)[col].charAt(0))) throw new BadConfigFormatException(layoutConfigFile);

				grid[row][col] = new BoardCell(row, col, rows.get(row)[col].charAt(0));

				if (rows.get(row)[col].length() > 1) {
					char specialType = rows.get(row)[col].charAt(1);
					BoardCell currCell = this.getCell(row, col);

					// Switch handles special cell types
					switch(specialType) {
					case '*':
						getCell(row, col).setCenter(true);
						getRoom(currCell.getInitial()).setCenterCell(currCell);
						break;
					case '#':
						getCell(row, col).setLabel(true);
						getRoom(currCell.getInitial()).setLabelCell(currCell);
						break;
					case '^':
						getCell(row, col).setDoorDirection(DoorDirection.UP);
						break;
					case 'v':
						getCell(row, col).setDoorDirection(DoorDirection.DOWN);
						break;
					case '<':
						getCell(row, col).setDoorDirection(DoorDirection.LEFT);
						break;
					case '>':
						getCell(row, col).setDoorDirection(DoorDirection.RIGHT);
						break;
					default:
						getCell(row, col).setSecretPassage(specialType);	
					}
				}
			}
		}

		// Initializes the adjacency list for all the cells in the grid
		for (int row = 0; row < grid.length; row++) {
			for(int col = 0; col < grid[row].length; col++) {
				if ((getRoom(row, col).getType() == Room.TileType.SPACE && getCell(row, col).getInitial() != 'X') || getCell(row, col).getSecretPassage() != ' ')
					getCell(row, col).setAdjacencies(this);
			}
		}

	}

	// Calculates all possible targets for the start cell given the pathlength
	public void calcTargets(BoardCell startCell, int pathlength) {
		visited.add(startCell);

		// Runs for all adjacent cells
		for (BoardCell cell : startCell.getAdjList()) {
			boolean test = true;
			
			// Testing to see if the adjacent cell  has already been visited
			for (BoardCell inVisited : visited) {
				if (inVisited.equals(cell)) { 
					test = false;
					break;
				}
			}
			
			// As long as the cell has not been visited and is not occupied (Unless it is a room center) then the cell is added to targets
			if (test && (!(cell.isOccupied()) || cell.isRoomCenter())) {
				visited.add(cell);
				if (cell.isRoomCenter()) {
					targets.add(cell);
					visited.remove(cell);
					continue;
				}

				// Recursive call until the pathlength is 1
				if (pathlength == 1) targets.add(cell);
				else calcTargets(cell, pathlength - 1);
				visited.remove(cell);
			}
		}
	}

	// Getters for board variables
	public void setConfigFiles(String layoutConfigFile, String setupConfigFile) {
		this.layoutConfigFile = new String("data/" + layoutConfigFile);
		this.setupConfigFile = new String("data/" + setupConfigFile);
	}

	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial());
	}

	public Room getRoom(Character room) {
		return roomMap.get(room);
	}

	public Room getRoom(int row, int col) {
		return roomMap.get(getCell(row, col).getInitial());
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
		Set<BoardCell> temp = targets;
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		return temp;
	}
}
