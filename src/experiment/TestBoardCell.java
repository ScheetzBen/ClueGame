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
	
	public void addAdjacency(TestBoardCell cell) {
		adjacencies.add(cell);
	}
	
	public Set<TestBoardCell> getAdjList() {
		return adjacencies;
	}
	
	public void setRoom(boolean isRoom) {
		this.isRoom = isRoom;
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
}
