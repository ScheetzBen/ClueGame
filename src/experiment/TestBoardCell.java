package experiment;

import java.util.*;

public class TestBoardCell {
	private int row, column;
	private Set<TestBoardCell> adjacencies = new HashSet<TestBoardCell>();
	private boolean isRoom = false, isOccupied = false;
	
	public TestBoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	
	public void addAdjacency(TestBoardCell cell) {
		adjacencies.add(cell);
	}
	
	public Set<TestBoardCell> getAdjList() {
//		if ((row - 1) >= 0) this.addAdjacency(new TestBoardCell(row - 1, column));
//		if ((row + 1) <= TestBoard.ROWS) this.addAdjacency(new TestBoardCell(row + 1, column));
//		if ((column - 1) >= 0) this.addAdjacency(new TestBoardCell(row, column - 1));
//		if ((column + 1) <= TestBoard.COLS) this.addAdjacency(new TestBoardCell(row, column + 1));
		
//		for (TestBoardCell i : adjacencies) {
//			System.out.println(i.row + " " + i.column);
//		}
		
		return adjacencies;
	}
	
	public void setRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public boolean isRoom() {
		return isRoom;
	}
	
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public boolean isOccupied() {
		return isOccupied;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof TestBoardCell)) return false;
		
		TestBoardCell c = (TestBoardCell) o;
		
		if (this.row == c.row && this.column == c.column) return true;
		return false;
	}
}
