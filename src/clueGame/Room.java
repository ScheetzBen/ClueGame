package clueGame;

public class Room {
	// Variables for Room
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	private TileType type;

	// enum to differentiate between Rooms and Spaces in the board
	enum TileType {
		ROOM, SPACE;
	}

	// Constructor sets the name of the Room
	public Room(String name, TileType type) {
		super();
		this.name = name;
		this.type = type;
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

	// Setters for Room variables
	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}

	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}
}
