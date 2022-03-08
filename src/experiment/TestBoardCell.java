package experiment;

import java.util.*;

public class TestBoardCell {
	// Variables for TestBoardCell
	private int row, column;
	private Set<TestBoardCell> adjacencies = new HashSet<TestBoardCell>();
	private boolean isRoom = false, isOccupied = false;
	
	// Constructor sets the row and column
	public TestBoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	
	// Adds and adjacency to the adjacency list
	public void addAdjacency(TestBoardCell cell) {
		adjacencies.add(cell);
	}
	
	// returns the adjacency list
	public Set<TestBoardCell> getAdjList() {
		return adjacencies;
	}
	
	// Sets whether or not the cell is a room
	public void setRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	
	// Initializes the adjacency list, used when a new TestBoard is created
	public void setAdjacencies(TestBoard board) {
		if ((this.row - 1) >= 0) this.addAdjacency(board.getCell(this.row - 1, this.column));
		if ((this.row + 1) < board.ROWS) this.addAdjacency(board.getCell(this.row + 1, this.column));
		if ((this.column - 1) >= 0) this.addAdjacency(board.getCell(this.row, this.column - 1));
		if ((this.column + 1) < board.COLS) this.addAdjacency(board.getCell(this.row, this.column + 1));
	}

	// Return the isRoom Boolean
	public boolean isRoom() {
		return isRoom;
	}
	
	// Sets whether or not the cell is occupied
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	// Returns the isOccupied boolean
	public boolean isOccupied() {
		return isOccupied;
	}
	
	// Used to test whether the row and column of a cell are equal
	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof TestBoardCell)) return false;
		
		TestBoardCell c = (TestBoardCell) o;
		
		if (this.row == c.row && this.column == c.column) return true;
		return false;
	}
}
