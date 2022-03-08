package clueGame;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	// Variables for BoardCell
		private int row, column;
		
		// initial holds the Room char, secretPassage holds the Room char for the secret passage is there is one
		private char initial, secretPassage;
		
		// doorDirection hold which way a door faces if there is one, is initialized to NONE because it will be changed if there is a door
		private DoorDirection doorDirection = DoorDirection.NONE;
		
		// roomLabel is true if this is where the roomLabel should go, roomCenter is true if the cell is in the Room center
		private boolean roomLabel = false, roomCenter = false;
		
		// isOccupied is true if the cell is currently occupied
		private boolean isOccupied = false;

		// Hold the cell's adjacencies, is populated in the board constructor
		private Set<BoardCell> adjacencies = new HashSet<BoardCell>();
		
		// Constructor sets the row and column, and requires and initial char
		public BoardCell(int row, int column, char initial) {
			super();
			this.row = row;
			this.column = column;
			this.initial = initial;
		}
		
		// Adds and adjacency to the adjacency list
		public void addAdjacency(BoardCell cell) {
			adjacencies.add(cell);
		}
		
		// returns the adjacency list
		public Set<BoardCell> getAdjList() {
			return adjacencies;
		}
		
		// Initializes the adjacency list, used when a new Board is created
		public void setAdjacencies(Board board) {
			if ((this.row - 1) >= 0) this.addAdjacency(board.getCell(this.row - 1, this.column));
			if ((this.row + 1) < board.getNumRows()) this.addAdjacency(board.getCell(this.row + 1, this.column));
			if ((this.column - 1) >= 0) this.addAdjacency(board.getCell(this.row, this.column - 1));
			if ((this.column + 1) < board.getNumColumns()) this.addAdjacency(board.getCell(this.row, this.column + 1));
		}

		// getters and setters for all BoardCell variables
		public char getInitial() {
			return initial;
		}

		public char getSecretPassage() {
			return secretPassage;
		}

		public void setSecretPassage(char secretPassage) {
			this.secretPassage = secretPassage;
		}

		public DoorDirection getDoorDirection() {
			return doorDirection;
		}
		
		public void setDoorDirection(DoorDirection doorDirection) {
			this.doorDirection = doorDirection;
		}

		public boolean isDoorway() {
			if (doorDirection != DoorDirection.NONE) return true;
			return false;
		}

		public boolean isLabel() {
			return roomLabel;
		}

		public void setLabel(boolean roomLabel) {
			this.roomLabel = roomLabel;
		}

		public boolean isRoomCenter() {
			return roomCenter;
		}

		public void setCenter(boolean roomCenter) {
			this.roomCenter = roomCenter;
		}
		
		public void setOccupied(boolean isOccupied) {
			
		}
		
		public boolean isOccupied() {
			return false;
		}
		
//		// Used to test whether the row and column of a cell are equal
//		@Override
//		public boolean equals(Object o) {
//			if (o == this) return true;
//			if (!(o instanceof BoardCell)) return false;
//			
//			BoardCell c = (BoardCell) o;
//			
//			if (this.row == c.row && this.column == c.column) return true;
//			return false;
//		}
}
