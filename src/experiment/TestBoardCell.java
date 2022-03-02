package experiment;

import java.util.Set;

public class TestBoardCell {
	private int row, column;
	private Set<TestBoardCell> adjacencies;
	private boolean isRoom = false, isOccupied = false;
	
	public TestBoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	
	void addAdjacency(TestBoardCell cell) {
		adjacencies.add(cell);
	}
	
	Set<TestBoardCell> getAdjList() {
		return adjacencies;
	}
	
	void setRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	
	boolean isRoom() {
		return isRoom;
	}
	
	void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	boolean isOccupied() {
		return isOccupied;
	}
}
