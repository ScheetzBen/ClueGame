package experiment;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class TestBoard {
	private TestBoardCell[][] grid = new TestBoardCell[ROWS][COLS];
	private Set<TestBoardCell> targets = new HashSet<TestBoardCell>();
	private Set<TestBoardCell> visited = new HashSet<TestBoardCell>();
	final static int ROWS = 4, COLS = 4;
	
	public TestBoard() {
		super();
		for (int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				grid[i][j] = new TestBoardCell(i, j);
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
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		if ((startCell.getRow() - 1) >= 0) startCell.addAdjacency(this.getCell(startCell.getRow() - 1, startCell.getColumn()));
		if ((startCell.getRow() + 1) < ROWS) startCell.addAdjacency(this.getCell(startCell.getRow() + 1, startCell.getColumn()));
		if ((startCell.getColumn() - 1) >= 0) startCell.addAdjacency(this.getCell(startCell.getRow(), startCell.getColumn() - 1));
		if ((startCell.getColumn() + 1) < COLS) startCell.addAdjacency(this.getCell(startCell.getRow(), startCell.getColumn() + 1));
		
//		for (TestBoardCell i : startCell.getAdjList()) {
//		System.out.println(i.getRow() + " " + i.getColumn());
//		}
		
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
				if (pathlength == 1) targets.add(j);
				else calcTargets(j, pathlength - 1);
				visited.remove(j);
			}
		}
	}
	
	public Set<TestBoardCell> getTargets() {
		Set<TestBoardCell> temp = targets;
		targets = new HashSet<TestBoardCell>();
		visited = new HashSet<TestBoardCell>();
		return temp;
	}
	
	public TestBoardCell getCell(int row, int col) {
		return grid[row][col];
	}
	
//	public static void main(String[] args) {
//		TestBoard test = new TestBoard();
//	}
}
