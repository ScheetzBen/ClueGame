package clueGame;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	// Variables for BoardCell
		private int row, column;
		private char initial, secretPassage;
		private DoorDirection doorDirection;
		private boolean roomLabel, roomCenter;

		private Set<BoardCell> adjacencies = new HashSet<BoardCell>();
		
//		private boolean isRoom = false, isOccupied = false;
		
		// Constructor sets the row and column
		public BoardCell(int row, int column) {
			super();
			this.row = row;
			this.column = column;
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
//		public void setAdjacencies(Board board) {
//			if ((this.row - 1) >= 0) this.addAdjacency(board.getCell(this.row - 1, this.column));
//			if ((this.row + 1) < board.getNumRows()) this.addAdjacency(board.getCell(this.row + 1, this.column));
//			if ((this.column - 1) >= 0) this.addAdjacency(board.getCell(this.row, this.column - 1));
//			if ((this.column + 1) < board.getNumColumns()) this.addAdjacency(board.getCell(this.row, this.column + 1));
//		}

		public char getSecretPassage() {
			return secretPassage;
		}

		public DoorDirection getDoorDirection() {
			return doorDirection;
		}
		
		public boolean isDoorway() {
			return false;
		}

		public boolean isLabel() {
//			return roomLabel;
			return false;
		}

		public void setLabel(boolean roomLabel) {
//			this.roomLabel = roomLabel;
		}

		public boolean isRoomCenter() {
//			return roomCenter;
			return false;
		}

		public void setCenter(boolean roomCenter) {
//			this.roomCenter = roomCenter;
		}
		
		
		
//		// Sets whether or not the cell is a room
//		public void setRoom(boolean isRoom) {
//			this.isRoom = isRoom;
//		}
//
//		// Return the isRoom Boolean
//		public boolean isRoom() {
//			return isRoom;
//		}
//		
//		// Sets whether or not the cell is occupied
//		public void setOccupied(boolean isOccupied) {
//			this.isOccupied = isOccupied;
//		}
//		
//		// Returns the isOccupied boolean
//		public boolean isOccupied() {
//			return isOccupied;
//		}
		
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
