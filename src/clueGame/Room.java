package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

public class Room {
	// Variables for Room
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	private TileType type;
	private Card card;
	private Set<BoardCell> roomCells;

	// enum to differentiate between Rooms and Spaces in the board
	enum TileType {
		ROOM, SPACE;
	}

	// Constructor sets the name of the Room
	public Room(String name, TileType type, Card card) {
		super();
		
		roomCells  = new HashSet<BoardCell>();
		
		this.name = name;
		this.type = type;
		this.card = card;
	}

	public Room(String name, TileType type) {
		this.name = name;
		this.type = type;
	}

	public void draw(int height, int width, int offset, Graphics g) {
		g.setColor(Color.BLUE);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		g.drawString(name, (labelCell.getColumn()) * width, (labelCell.getRow() + offset) * height + height);
	}
	
	public boolean withinRoom(int x, int y) {
		for (BoardCell cell : roomCells) {
			if (cell.withinCell(x, y)) return true;
		}
		
		return false;
	}
	
	// Getters for Room variables
	public String getName() {
		return name;
	}

	public BoardCell getCenterCell() {
		return centerCell;
	}

	public TileType getType() {
		return type;
	}

	public BoardCell getLabelCell() {
		return labelCell;
	}

	public Card getCard() {
		return card;
	}

	// Setters for Room variables
	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
		roomCells.add(centerCell);
	}

	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
		roomCells.add(labelCell);
	}
	
	public void addCell(BoardCell cell) {
		roomCells.add(cell);
	}
}
