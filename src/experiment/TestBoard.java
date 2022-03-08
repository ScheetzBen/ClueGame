package experiment;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class TestBoard {
	// Variables for TestBoard make a grid an empty list for target and visited cells
	private TestBoardCell[][] grid = new TestBoardCell[ROWS][COLS];
	private Set<TestBoardCell> targets = new HashSet<TestBoardCell>();
	private Set<TestBoardCell> visited = new HashSet<TestBoardCell>();
	// Hard coded ROWS and COLS for TestBoard size
	final static int ROWS = 4, COLS = 4;
	
	// Constructor for TestBoard creates new TestBoardCells for all spots in the grid
	// Also calls setAdjacencies for all cells in the grid
	public TestBoard() {
		super();
		for (int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				grid[i][j] = new TestBoardCell(i, j);
			}
		}
		
		for (int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				grid[i][j].setAdjacencies(this);
			}
		}
		
		// code for when we need to 
//		try {
//			FileReader reader = new FileReader("ClueLayout.csv");
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		
//		Scanner in = new Scanner("ClueLayout.csv");
//		in.useDelimiter(",");
//		
//		while (in.hasNext()) {
//			int currRow = 0, currColumn = 0;
//			TestBoardCell currCell = new TestBoardCell(currRow, currColumn);
//			if (in.next() == "X" || in.next() == "W") board.add(currCell);
//			
//		}
	}
	
	// Calculates all possible targets for the start cell given the pathlength
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		visited.add(startCell);
		
		Set<TestBoardCell> temp = startCell.getAdjList();
		
		for (TestBoardCell j : temp) {
			boolean test = true;
			for (TestBoardCell i : visited) {
				if (i.equals(j)) { 
					test = false;
					break;
				}
			}
			if (test && !(j.isOccupied())) {
				visited.add(j);
				if (j.isRoom()) {
					targets.add(j);
					visited.remove(j);
					continue;
				}
				if (pathlength == 1) targets.add(j);
				else calcTargets(j, pathlength - 1);
				visited.remove(j);
			}
		}
	}
	
	// Resets the targets and visited sets and returns the targets
	public Set<TestBoardCell> getTargets() {
		Set<TestBoardCell> temp = targets;
		targets = new HashSet<TestBoardCell>();
		visited = new HashSet<TestBoardCell>();
		return temp;
	}
	
	// Return a cell in the grid
	public TestBoardCell getCell(int row, int col) {
		return grid[row][col];
	}
}
