package clueGame;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	// Variables for BoardCell
	private int row, column;

	// initial holds the Room char, secretPassage holds the Room char for the secret passage is there is one
	private char initial, secretPassage = ' ';

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
		BoardCell currCell;

		if ((this.row - 1) >= 0) {
			currCell = board.getCell(row - 1, column);
			if (board.getRoom(currCell).getType() != Room.TileType.ROOM && currCell.getInitial() != 'X') this.addAdjacency(currCell);
		}

		if ((this.row + 1) < board.getNumRows()) {
			currCell = board.getCell(row + 1, column);
			if (board.getRoom(currCell).getType() != Room.TileType.ROOM && currCell.getInitial() != 'X') this.addAdjacency(currCell);
		}

		if ((this.column - 1) >= 0) {
			currCell = board.getCell(row, column - 1);
			if (board.getRoom(currCell).getType() != Room.TileType.ROOM && currCell.getInitial() != 'X') this.addAdjacency(currCell);
		}

		if ((this.column + 1) < board.getNumColumns()) {
			currCell = board.getCell(row, column + 1);
			if (board.getRoom(currCell).getType() != Room.TileType.ROOM && currCell.getInitial() != 'X') this.addAdjacency(currCell);
		}

		// Handles doorway adjacency
		if (this.isDoorway()) {
			var direction = this.getDoorDirection().toString();

			switch(direction) {
			case "^":
				currCell = board.getCell(row - 1, column);
				this.addAdjacency(board.getRoom(currCell).getCenterCell());
				board.getRoom(currCell).getCenterCell().addAdjacency(this);
				break;
			case "v":
				currCell = board.getCell(row + 1, column);
				this.addAdjacency(board.getRoom(currCell).getCenterCell());
				board.getRoom(currCell).getCenterCell().addAdjacency(this);
				break;
			case "<":
				currCell = board.getCell(row, column - 1);
				this.addAdjacency(board.getRoom(currCell).getCenterCell());
				board.getRoom(currCell).getCenterCell().addAdjacency(this);
				break;
			case ">":
				currCell = board.getCell(row, column + 1);
				this.addAdjacency(board.getRoom(currCell).getCenterCell());
				board.getRoom(currCell).getCenterCell().addAdjacency(this);
				break;
			}
		}

		// Handles secret passage adjacency
		if (this.getSecretPassage() != ' ') {
			currCell = board.getRoom(this).getCenterCell();

			currCell.addAdjacency(board.getRoom(this.getSecretPassage()).getCenterCell());
		}
	}

	// getters and setters for all BoardCell variables
	public char getInitial() {
		return initial;
	}

	public char getSecretPassage() {
		return secretPassage;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public boolean isDoorway() {
		if (doorDirection != DoorDirection.NONE) return true;
		return false;
	}

	public boolean isLabel() {
		return roomLabel;
	}

	public boolean isRoomCenter() {
		return roomCenter;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	//Setters for board variables
	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}

	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}


	public void setLabel(boolean roomLabel) {
		this.roomLabel = roomLabel;
	}

	public void setCenter(boolean roomCenter) {
		this.roomCenter = roomCenter;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	// Used to test whether the row and column of a cell are equal
	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof BoardCell)) return false;

		BoardCell c = (BoardCell) o;

		if (this.row == c.row && this.column == c.column) return true;
		return false;
	}
}
