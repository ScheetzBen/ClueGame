package experiment;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class TestBoard {
	Set<TestBoardCell> board = new HashSet<TestBoardCell>();
	private static int ROWS = 26, COLUMNS = 23;
	
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
		return;
	}
	
	public Set<TestBoardCell> getTargets() {
		return new HashSet<TestBoardCell>();
	}
	
	public TestBoardCell getCell(int row, int col) {
		return new TestBoardCell(0,0);
	}
}
