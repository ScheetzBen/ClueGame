package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
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
	
	private boolean flag = false;

	// Hold the cell's adjacencies, is populated in the board constructor
	private Set<BoardCell> adjacencies = new HashSet<BoardCell>();

	// Constructor sets the row and column, and requires and initial char
	public BoardCell(int row, int column, char initial) {
		super();
		this.row = row;
		this.column = column;
		this.initial = initial;
	}

	// returns the adjacency list
	public Set<BoardCell> getAdjList() {
		return adjacencies;
	}

	// Initializes the adjacency list, used when a new Board is created
	public void setAdjacencies(Board board) {
		addAdjacency(true, 1, board);

		addAdjacency(true, -1, board);

		addAdjacency(false, 1, board);

		addAdjacency(false, -1, board);
		
		var direction = this.getDoorDirection().toString();

		switch(direction) {
		case "^":
			addDoorway(true, -1, board);
			break;
		case "v":
			addDoorway(true, 1, board);
			break;
		case "<":
			addDoorway(false, -1, board);
			break;
		case ">":
			addDoorway(false, 1, board);
			break;
		}

		// Handles secret passage adjacency
		if (this.getSecretPassage() != ' ') {
			BoardCell currCell = board.getRoom(this).getCenterCell();

			currCell.adjacencies.add(board.getRoom(this.getSecretPassage()).getCenterCell());
		}
	}
	
	// Helper class to remove code duplication
	// checks if cell is valid and adds it to the adjacency list
	private void addAdjacency(boolean isRow, int where, Board board) {
		BoardCell currCell = null;
		
		if (isRow) {
			if (row + where >= 0 && row + where < board.getNumRows()) currCell = board.getCell(row + where, column);
		} else {
			if (column + where >= 0 && column + where < board.getNumColumns()) currCell = board.getCell(row, column + where);
		}
		
		if (currCell != null)
			if (board.getRoom(currCell).getType() != Room.TileType.ROOM && currCell.getInitial() != 'X') this.adjacencies.add(currCell);
	}
	
	// Helper class to remove code duplication
	// Properly handles doorway adjacencies and adds the doorway to the room adjacencies
	private void addDoorway(boolean isRow, int where, Board board) {
		BoardCell currCell;
		
		if (isRow) currCell = board.getCell(row + where, column);
		else currCell = board.getCell(row, column + where);

		this.adjacencies.add(board.getRoom(currCell).getCenterCell());
		board.getRoom(currCell).getCenterCell().adjacencies.add(this);
	}
	
	// Function which allows the cell to draw itself in the board Panel
	public void draw(int height, int width, Graphics g) {
		switch (initial) {
			case 'X':
				break;
			case 'W':
				if (flag)
					g.setColor(Color.CYAN);
				else
					g.setColor(Color.YELLOW);
				
				g.fillRect((column + 1) * width, (row + 1) * height, width - 2, height - 2);
				
				if (isDoorway()) {
					g.setColor(Color.BLUE);
					switch (doorDirection) {
					case UP:
						g.fillRect((column + 1) * width, ((row + 1) * height) - 4, width - 2, 3);
						break;
					case DOWN:
						g.fillRect((column + 1) * width, (row + 1) * height + height - 1, width - 2, 3);
						break;
					case LEFT:
						g.fillRect(((column + 1) * width) - 4, ((row + 1) * height), 3, height - 2);
						break;
					case RIGHT:
						g.fillRect(((column + 1) * width) + width - 1, (row + 1) * height, 3, height - 2);
						break;
					default:
						break;
					}
				}
				
				break;
			default:
				if (getSecretPassage() != ' ') {
					g.setColor(Color.YELLOW);
					g.fillRect(((column + 1) * width) + 2, ((row + 1) * height) + 5, width - 6, height - 5);
					g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
					g.setColor(Color.BLUE);
					g.drawString("S", ((column + 1) * width) + width / 3, ((row + 2) * height) - height / 4);
					break;
				}
				
				g.setColor(Color.GRAY);
				g.fillRect(((column + 1) * width) - 1, ((row + 1) * height) - 1, width, height);
		}
	}

	// Getters for BoardCell
	public char getInitial() {
		return initial;
	}

	public char getSecretPassage() {
		return secretPassage;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
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

	// Setters for board variables
	public void flag() {
		flag = true;
	}
	
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
}
