package experiment;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class TestBoard {
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets = new HashSet<TestBoardCell>();
	private Set<TestBoardCell> visited = new HashSet<TestBoardCell>();
	private static int ROWS = 4, COLS = 4;
	
	public TestBoard() {
		super();
		
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
		startCell.addAdjacency(new TestBoardCell(startCell.getRow() - 1, startCell.getColumn()));
		startCell.addAdjacency(new TestBoardCell(startCell.getRow() + 1, startCell.getColumn()));
		startCell.addAdjacency(new TestBoardCell(startCell.getRow(), startCell.getColumn() - 1));
		startCell.addAdjacency(new TestBoardCell(startCell.getRow(), startCell.getColumn() + 1));

		for (TestBoardCell i : startCell.getAdjList()) {
			for (TestBoardCell j : visited) {
				if (i == j) break;
			}
			visited.add(i);
			if (pathlength == 1) targets.add(i);
			else calcTargets(i, pathlength - 1);
			visited.remove(i);
		}
	}
	
	public Set<TestBoardCell> getTargets() {
		return new HashSet<TestBoardCell>();
	}
	
	public TestBoardCell getCell(int row, int col) {
		return new TestBoardCell(0,0);
	}
}
